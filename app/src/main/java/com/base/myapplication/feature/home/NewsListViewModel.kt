package com.base.myapplication.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.base.myapplication.data.repository.NewsRepository

class NewsListViewModel(private val repo: NewsRepository) : ViewModel() {
    fun getNews() = repo.getData("tesla","5e51183454864effa9c541985ab6701c").asLiveData(viewModelScope.coroutineContext)
}