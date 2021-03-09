package com.base.myapplication.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.base.myapplication.data.model.ResponseData
import com.base.myapplication.network.Constant.APIKEY

interface ApiInterface {

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") q: String?,
        @Query("page") page: Int?,
        @Query("apiKey") apiKey: String? = APIKEY
    ): Response<ResponseData>
}