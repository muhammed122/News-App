package com.example.newsapplinktask.dispatcher.article

import com.example.newsapplinktask.repository.remote.article.ArticleRemoteRepo

class ArticleDispatcherImpl(
    override val remoteRepo: ArticleRemoteRepo
) : ArticleDispatcher {

}