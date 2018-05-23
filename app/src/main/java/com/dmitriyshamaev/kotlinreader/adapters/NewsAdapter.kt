package com.dmitriyshamaev.kotlinreader.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dmitriyshamaev.kotlinreader.R
import com.dmitriyshamaev.kotlinreader.databinding.NewsListItemBinding
import com.dmitriyshamaev.kotlinreader.model.NewsItem

class NewsAdapter(private val clickListener: (NewsItem) -> Unit): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var mNewsList = listOf<NewsItem>()
    private lateinit var mContext: Context

    fun setNewsList(newsList: List<NewsItem>) {
        if (mNewsList.isEmpty()) {
            mNewsList = newsList
            notifyItemRangeInserted(0, newsList.size)
        } else {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mNewsList.size
                }

                override fun getNewListSize(): Int {
                    return newsList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = mNewsList[oldItemPosition]
                    val newsItem = newsList[newItemPosition]
                    return old.id === newsItem.id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = mNewsList[oldItemPosition]
                    val newsItem = newsList[newItemPosition]
                    return (old.id === newsItem.id
                            && old.title === newsItem.title
                            && old.description === newsItem.description
                            && old.imageURL === newsItem.imageURL
                            && old.pubDate == newsItem.pubDate)
                }
            })
            mNewsList = newsList
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = DataBindingUtil.inflate<NewsListItemBinding>(LayoutInflater.from(parent.context), R.layout.news_list_item, parent, false)
        mContext = parent.context
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = mNewsList[position]
        holder.binding.newsItem = newsItem
        holder.binding.root.setOnClickListener { clickListener(newsItem) }
        holder.binding.executePendingBindings()

        if (newsItem.imageURL != null) {
            Glide.with(mContext).load(Uri.parse(newsItem.imageURL)).into(holder.binding.newsImage)
        }
    }

    class NewsViewHolder(val binding: NewsListItemBinding): android.support.v7.widget.RecyclerView.ViewHolder(binding.root)

}