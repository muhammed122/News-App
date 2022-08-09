package com.example.newsapplinktask.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticlesResponse(
    val payload: ArticlesPayload? = null,
) : BaseModel()

data class ArticlesPayload(
    val articles: List<ArticlesItem>
) : BaseModel.Payload()

data class ArticlesItem(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Serializable
