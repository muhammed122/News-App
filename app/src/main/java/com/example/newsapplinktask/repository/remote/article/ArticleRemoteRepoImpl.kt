package com.example.newsapplinktask.repository.remote.article

import com.example.newsapplinktask.model.request.BaseRequestFactory
import com.example.newsapplinktask.model.request.article.GetArticleRequestFactory
import com.example.newsapplinktask.model.request.article.GetArticleRequestParam
import com.example.newsapplinktask.model.response.BaseModel
import com.example.newsapplinktask.model.retrofit.Api
import retrofit2.Response

class ArticleRemoteRepoImpl(private val api: Api) : ArticleRemoteRepo {

    override suspend fun fetchData(requestFactory: BaseRequestFactory): Response<out BaseModel>? {
        return when (requestFactory) {
            is GetArticleRequestFactory -> {
                val param = requestFactory.baseRequestParam as GetArticleRequestParam
                api.getArticles(
                    requestFactory.getUrl(),
                    requestFactory.getHeaderParam(),
                    apiKey = param.apiKey,
                    source = param.source
                )
            }
            else -> {
                null
            }
        }

    }
}