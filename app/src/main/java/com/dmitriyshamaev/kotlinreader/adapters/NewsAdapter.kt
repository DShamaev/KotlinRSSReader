package com.dmitriyshamaev.kotlinreader.adapters

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.net.Uri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dmitriyshamaev.kotlinreader.R
import com.dmitriyshamaev.kotlinreader.databinding.NewsListItemBinding
import com.dmitriyshamaev.kotlinreader.model.NewsItem

class NewsAdapter(private val clickListener: (NewsItem) -> Unit): ListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(diffCallback) {

    private lateinit var mContext: Context

    class NewsItemCallback: DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.id === newItem.id

        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return (oldItem.id === newItem.id
                    && oldItem.title === newItem.title
                    && oldItem.description === newItem.description
                    && oldItem.imageURL === newItem.imageURL
                    && oldItem.pubDate == newItem.pubDate)

        }
    }

    companion object {
        val diffCallback = NewsItemCallback()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = DataBindingUtil.inflate<NewsListItemBinding>(LayoutInflater.from(parent.context), R.layout.news_list_item, parent, false)
        mContext = parent.context
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.binding.newsItem = newsItem
        holder.binding.root.setOnClickListener { clickListener(newsItem) }
        holder.binding.executePendingBindings()

        if (newsItem.imageURL != null) {
            Glide.with(mContext).load(Uri.parse(newsItem.imageURL)).into(holder.binding.newsImage)
        }
    }

    class NewsViewHolder(val binding: NewsListItemBinding): androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

}