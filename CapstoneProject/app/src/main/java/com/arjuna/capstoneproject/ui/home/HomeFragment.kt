package com.arjuna.capstoneproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val newsViewModel by viewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfile.text = firebaseAuth.currentUser?.displayName

        binding.imgNews.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, NewsFragment())
                .commit()
        }

        newsViewModel.getNews()
        newsViewModel.newsList.observe(viewLifecycleOwner, {
            binding.rvNews.apply {
                val newsAdapter = NewsAdapter(it)

                adapter = newsAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        })

    }
}