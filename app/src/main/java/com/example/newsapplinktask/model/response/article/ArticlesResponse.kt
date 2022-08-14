package com.example.newsapplinktask.model.response.article

import com.example.newsapplinktask.model.response.BaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticlesResponse(

    @SerializedName("articles")
    val payload: List<ArticlesItem>? = null,
) : BaseModel()



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
