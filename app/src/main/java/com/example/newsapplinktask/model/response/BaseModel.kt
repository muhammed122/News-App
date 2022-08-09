package com.example.newsapplinktask.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseModel : Serializable {
    val status: String? = null
    val code: String? = null
    val message: String? = null
    open class Payload : Serializable
    val sortBy: String? = null
    val source: String? = null

}