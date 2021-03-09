package com.base.myapplication.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.base.myapplication.base.BaseFragment
import com.base.myapplication.data.model.Article
import com.base.myapplication.databinding.FragmentListBinding
import com.base.myapplication.network.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsListFragment : BaseFragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NewsListViewModel>()
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private var page = 1
    private var lastPage = 1
    private var dataHeadline = Article()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        getData()
    }

    private fun initView() {
        initRecycleView(binding.rvNews, newsAdapter)
    }

    private fun initListeners() {
        newsAdapter.gotoDetail = {
            moveTo(NewsListFragmentDirections.actionNewsListFragmentToDetailFragment(toJson(it)))
        }

        binding.imgHeadline.setOnClickListener {
            moveTo(NewsListFragmentDirections.actionNewsListFragmentToDetailFragment(toJson(dataHeadline)))
        }

        binding.swipeRefresh.setOnRefreshListener {
            page = 1
            binding.swipeRefresh.isRefreshing = false
            getData()
        }

        binding.nestedScroll.setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                    page++
                    if (page <= lastPage) {
                        visible(binding.lazyload)
                        getData()
                    }
                }
            }
        }
    }

    private fun getData() {
        viewModel.getNews(page).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (page == 1) {
                        newsAdapter.clear()
                        it.data?.articles?.let { data ->
                            dataHeadline = data[0]
                            loadImage(binding.imgHeadline, data[0].urlToImage)
                            binding.tvHeadlineTitle.text = data[0].title
                            newsAdapter.addAll(data)
                            newsAdapter.removeAt(0)
                        }
                        lastPage = it.data!!.totalResults
                    } else {
                        it.data?.articles?.let { data ->
                            newsAdapter.addAll(data)
                        }
                    }
                    newsAdapter.notifyDataSetChanged()
                    gone(binding.lazyload)
                    finishedLoading()
                }
                Resource.Status.ERROR -> {
                    it.message?.let { it1 -> failureTask(it1) }
                    gone(binding.lazyload)
                    finishedLoading()
                }
                Resource.Status.LOADING -> {
                    if (page == 1) startLoading()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}