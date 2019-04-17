package com.dmitriyshamaev.kotlinreader.ui

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.dmitriyshamaev.kotlinreader.R
import com.dmitriyshamaev.kotlinreader.adapters.NewsAdapter
import com.dmitriyshamaev.kotlinreader.databinding.FragmentNewsBinding
import com.dmitriyshamaev.kotlinreader.model.NewsItem
import com.dmitriyshamaev.kotlinreader.viewmodel.NewsListViewModel
import org.koin.android.ext.android.inject

class NewsFragment: Fragment() {

    private lateinit var mNewsAdapter: NewsAdapter

    private lateinit var mBinding: FragmentNewsBinding

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel : NewsListViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        mNewsAdapter = NewsAdapter { newsItem ->
            val directions = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(newsItemId = newsItem.id!!.toInt())
            NavHostFragment.findNavController(this).navigate(directions)
        }
        mBinding.newsList.adapter = mNewsAdapter
        linearLayoutManager = LinearLayoutManager(this.context)
        mBinding.newsList.layoutManager = linearLayoutManager

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: NewsListViewModel) {
        // Update the list when the data changes
        viewModel.news.observe(this, Observer<List<NewsItem>> { newsList ->
            if (newsList != null) {
                mBinding.isLoading = false
                mNewsAdapter.submitList(newsList)
            } else {
                mBinding.isLoading = true
            }
            mBinding.executePendingBindings()
        })
    }
}