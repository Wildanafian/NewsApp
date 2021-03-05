package com.base.myapplication.di

import org.koin.dsl.module
import retrofit2.Retrofit
import com.base.myapplication.network.ApiInterface

val apiModule = module {
    fun provideApi(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    single { provideApi(get()) }
}