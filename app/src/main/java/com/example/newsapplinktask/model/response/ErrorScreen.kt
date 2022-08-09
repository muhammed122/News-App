package com.example.newsapplinktask.model.response

import com.example.newsapplinktask.model.request.BaseRequestParam

data class ErrorScreen(
    val title: String,
    val message: String?,
    val responseCode: String?,
    val renderType: String,
    val endPoint: String?,
    val requestParam: BaseRequestParam
) {


    constructor(message: String?, responseCode: String?, endPoint: String?,requestParam: BaseRequestParam) : this("", message, responseCode, "",endPoint,requestParam)
}