package com.arjuna.capstoneproject.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.FragmentHomeBinding
import com.arjuna.capstoneproject.ui.news.NewsAdapter
import com.arjuna.capstoneproject.ui.news.NewsFragment
import com.arjuna.capstoneproject.ui.news.NewsViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfile.text = firebaseAuth.currentUser?.displayName

        binding.imgNews.setOnClickListener { showNotImplemented() }
        binding.imgTips.setOnClickListener { showNotImplemented() }
        binding.imgReportKu.setOnClickListener { showNotImplemented() }
        binding.imgSocialMedia.setOnClickListener { showNotImplemented() }

        newsViewModel.getNews()
        newsViewModel.newsList.observe(viewLifecycleOwner, {
            binding.rvNews.apply {
                val newsAdapter = NewsAdapter(it)

                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        })
        newsViewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) binding.shimmerLayout.startShimmerAnimation() else binding.shimmerLayout.stopShimmerAnimation()
            binding.shimmerLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun showNotImplemented() {
        Toast.makeText(requireContext(), "Not Implemented yet!", Toast.LENGTH_SHORT).show()
    }
}