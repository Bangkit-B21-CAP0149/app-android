package com.arjuna.capstoneproject.ui.faq

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arjuna.capstoneproject.data.local.entity.FaqEntity
import com.arjuna.capstoneproject.databinding.FaqListItemBinding

class FaqAdapter(private val callback: FaqCallback) :
    RecyclerView.Adapter<FaqAdapter.ViewHolder>() {
    private val listDataFaq = ArrayList<FaqEntity>()
    fun setDataFaq(dataFaq: List<FaqEntity>?) {
        if (dataFaq == null)
            return listDataFaq.clear()
        listDataFaq.addAll(dataFaq)
    }

    inner class ViewHolder(private val binding: FaqListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataFaq: FaqEntity) {
            with(binding) {
                tvQuestion.text = dataFaq.questions
                tvAnswer.text = dataFaq.answerFaq

                cardItem.setOnClickListener {
                    callback.onItemClicked(dataFaq)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FaqListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqAdapter.ViewHolder, position: Int) {
        holder.bind(listDataFaq[position])
    }

    override fun getItemCount(): Int {
        return listDataFaq.size
    }
}