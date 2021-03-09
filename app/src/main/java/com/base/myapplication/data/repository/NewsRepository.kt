package com.base.myapplication.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import com.base.myapplication.data.model.ResponseData
import com.base.myapplication.network.ApiInterface
import com.base.myapplication.network.BaseDataSource
import com.base.myapplication.network.Resource

class NewsRepository(private val api: ApiInterface) : BaseDataSource() {
    fun getData(q: String, page: Int): Flow<Resource<ResponseData>> {
        return flow {
            emit(getResult { api.getAllNews(q, page) })
        }.onStart { emit(Resource.loading()) }.flowOn(Dispatchers.IO)
    }
}