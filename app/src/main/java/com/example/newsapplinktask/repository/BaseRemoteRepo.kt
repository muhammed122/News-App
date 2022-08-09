package com.example.newsapplinktask.repository

import com.example.newsapplinktask.model.request.BaseRequestFactory
import com.example.newsapplinktask.model.response.BaseModel
import retrofit2.Response

interface BaseRemoteRepo {
    suspend fun  fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>?
}