package com.arjuna.capstoneproject.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjuna.capstoneproject.databinding.FragmentFaqBinding

class FaqFragment : Fragment() {

    private lateinit var viewModel: FaqViewModel

    private val binding: FragmentFaqBinding by lazy {
        FragmentFaqBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(
                it, ViewModelProvider.NewInstanceFactory()
            )[FaqViewModel::class.java]
        }

        val listofFaq = viewModel.getListFaq()
        binding.rvFaq.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FaqAdapter()
            setHasFixedSize(true)
        }.also {
            it.adapter.let { adapter ->
                when (adapter) {
                    is FaqAdapter -> {
                        adapter.setDataFaq(listofFaq)
                    }
                }
            }
        }

    }
}