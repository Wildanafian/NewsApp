package com.base.myapplication.di

import org.koin.dsl.module
import com.base.myapplication.data.repository.NewsRepository

val repoModule = module {
    single { NewsRepository(get()) }
}