package com.base.myapplication.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.base.myapplication.R
import com.base.myapplication.base.BaseActivity
import com.base.myapplication.feature.detail.Detail
import com.base.myapplication.network.Resource

class NewsListActivity : BaseActivity(R.layout.activity_main) {

    private val newsAdapter: NewsAdapter = NewsAdapter()
    private val viewModel by viewModel<NewsListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListeners()
        initObserveable()
    }

    private fun initView() {
        rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun initListeners() {
        newsAdapter.gotoDetail = {
            startActivity(Intent(this, Detail::class.java).putExtra("data", Gson().toJson(it)))
        }
    }

    private fun initObserveable() {
        viewModel.getNews().observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    newsAdapter.clear()
                    it.data.let { data ->
                        data?.articles?.let { it1 -> newsAdapter.addAll(it1) }
                    }
                    newsAdapter.notifyDataSetChanged()
                    progress_bar.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    it.message?.let { it1 -> failureTask(it1) }
                    progress_bar.visibility = View.GONE
                }
                Resource.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        })
    }
}