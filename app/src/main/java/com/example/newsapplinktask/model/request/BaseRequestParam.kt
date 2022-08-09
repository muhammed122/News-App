package com.example.newsapplinktask.model.request

import com.example.newsapplinktask.model.Constant
import java.io.Serializable

open class BaseRequestParam(var apiKey: String?=Constant.API_KEY) : Serializable
