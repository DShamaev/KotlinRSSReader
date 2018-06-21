package com.dmitriyshamaev.kotlinreader.ui

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmitriyshamaev.kotlinreader.R
import com.dmitriyshamaev.kotlinreader.databinding.FragmentNewsDetailsBinding
import com.dmitriyshamaev.kotlinreader.model.NewsItem
import com.dmitriyshamaev.kotlinreader.viewmodel.NewsListViewModel
import org.koin.android.ext.android.inject

class NewsDetailsFragment: androidx.fragment.app.Fragment() {

    private lateinit var mBinding: FragmentNewsDetailsBinding
    private val viewModel : NewsListViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: NewsListViewModel) {
        val args = NewsDetailsFragmentArgs.fromBundle(arguments)

        // Update the list when the data changes
        viewModel.getNewsItem(args.newsItemId.toLong()).observe(this, Observer<NewsItem> { newsItem ->
            if (newsItem != null) {
                mBinding.newsItem = newsItem
                mBinding.newsItemDescription.loadData(newsItem.description,"text/html", "utf-8")
            }
            mBinding.executePendingBindings()
        })
    }

}