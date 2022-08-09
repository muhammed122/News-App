package com.example.newsapplinktask.model.retrofit

import com.example.newsapplinktask.model.response.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query
import retrofit2.http.Url


interface Api {

    @GET
    suspend fun getArticles(
        @Url ure: String,
        @HeaderMap headers: Map<String, String>,
        @Query("source") source: String,
        @Query("apiKey") apiKey: String?,
    ): Response<ArticlesResponse>?

}