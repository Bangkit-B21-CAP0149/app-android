package com.arjuna.capstoneproject.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.arjuna.capstoneproject.data.remote.ArticlesItem
import com.arjuna.capstoneproject.databinding.NewsListItemBinding

class NewsAdapter(private val news: List<ArticlesItem?>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem) {
            with(binding) {
                tvNewsTitle.text = news.title
                tvNewsDate.text = news.publishedAt
                tvNewsDesc.text = news.description
                imgNewsItem.load(news.urlToImage) {
                    transformations(RoundedCornersTransformation(18f))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        news[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = news.size

}