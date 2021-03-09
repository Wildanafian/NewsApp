package com.base.myapplication.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.base.myapplication.data.repository.NewsRepository

class NewsListViewModel(private val repo: NewsRepository) : ViewModel() {
    fun getNews(page: Int) = repo.getData("tesla", page).asLiveData(viewModelScope.coroutineContext)
}