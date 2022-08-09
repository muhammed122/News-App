package com.example.newsapplinktask.model.request.article

import com.example.newsapplinktask.model.request.BaseRequestParam

data class GetArticleRequestParam(
val source :String
) : BaseRequestParam()