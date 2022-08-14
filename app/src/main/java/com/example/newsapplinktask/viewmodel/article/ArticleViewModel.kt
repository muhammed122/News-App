package com.example.newsapplinktask.viewmodel.article

import androidx.lifecycle.MutableLiveData
import com.example.newsapplinktask.dispatcher.BaseDispatcher
import com.example.newsapplinktask.dispatcher.article.ArticleDispatcher
import com.example.newsapplinktask.model.Constant.SOURCE_ONE
import com.example.newsapplinktask.model.request.article.GetArticleRequestFactory
import com.example.newsapplinktask.model.request.article.GetArticleRequestParam
import com.example.newsapplinktask.model.response.article.ArticlesItem
import com.example.newsapplinktask.model.response.article.ArticlesResponse
import com.example.newsapplinktask.viewmodel.BaseViewModel
import kotlinx.coroutines.channels.Channel
import androidx.lifecycle.viewModelScope
import com.example.newsapplinktask.model.Constant.SOURCE_TWO
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleDispatcher: ArticleDispatcher) : BaseViewModel() {

    override fun getDispatcher(): BaseDispatcher = articleDispatcher

    val newsLiveData = MutableLiveData<List<ArticlesItem>>()
    private val newsChannel = Channel<List<ArticlesItem>>()

    fun getNews() {

        fetchData(
            ArticlesResponse::class.java,
            GetArticleRequestFactory(GetArticleRequestParam(SOURCE_ONE)),
            showLoading = true,
            showError = true
        ) {
            if (it is ArticlesResponse)
                viewModelScope.launch {
                    newsChannel.send(it.payload!!)
                }
        }

        fetchData(
            ArticlesResponse::class.java,
            GetArticleRequestFactory(GetArticleRequestParam(SOURCE_TWO)),
            showLoading = true,
            showError = true
        ) {
            if (it is ArticlesResponse)
                viewModelScope.launch {
                    newsChannel.send(it.payload!!)
                }
        }

        viewModelScope.launch {
            for (data in newsChannel)
                newsLiveData.postValue(data)
        }

    }

}