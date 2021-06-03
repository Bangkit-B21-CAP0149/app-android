package com.arjuna.capstoneproject.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjuna.capstoneproject.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private val binding: FragmentNewsBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        )[NewsViewModel::class.java]

        viewModel.getNews()
        viewModel.newsList.observe(viewLifecycleOwner, {

            binding.rvNews.apply {
                adapter = NewsAdapter(it)
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                binding.shimmerLayout.startShimmerAnimation()
                binding.rvNews.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmerAnimation()
                binding.rvNews.visibility = View.VISIBLE
            }
            binding.shimmerLayout.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}