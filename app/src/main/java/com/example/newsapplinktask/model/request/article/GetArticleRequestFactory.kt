package com.example.newsapplinktask.model.request.article

import com.example.newsapplinktask.model.request.BaseRequestFactory
import com.example.newsapplinktask.model.request.BaseRequestParam
import com.example.newsapplinktask.model.retrofit.EndPoints

class GetArticleRequestFactory(getArticleRequestParam: GetArticleRequestParam) :
    BaseRequestFactory() {

    override var baseRequestParam: BaseRequestParam = getArticleRequestParam
    override fun getEndPoint(): String = EndPoints.ARTICLES
}