package com.base.myapplication.di
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.base.myapplication.feature.home.NewsListViewModel


val viewModel = module {
    viewModel { NewsListViewModel(get()) }
}