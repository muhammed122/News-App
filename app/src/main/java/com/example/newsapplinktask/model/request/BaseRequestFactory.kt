package com.example.newsapplinktask.model.request

import com.example.newsapplinktask.model.Constant
import java.util.*
import kotlin.collections.HashMap

abstract class BaseRequestFactory {
    abstract var baseRequestParam: BaseRequestParam
    private var defaultProperties = HashMap<String, String>()

    init {
        defaultProperties["Accept"] = "application/json"
        defaultProperties["Connection"] = "close"
    }

    open fun getUrl() = Constant.BASE_URL + getEndPoint()
    abstract fun getEndPoint(): String?

    fun getHeaderParam(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers.putAll(defaultProperties)
        headers.putAll(getCustomHeaders())
        return headers
    }

    open fun getCustomHeaders(): HashMap<String, String> = HashMap()


}