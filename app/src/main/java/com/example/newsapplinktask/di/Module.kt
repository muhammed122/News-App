package com.example.newsapplinktask.di
import com.example.newsapplinktask.dispatcher.article.ArticleDispatcher
import com.example.newsapplinktask.dispatcher.article.ArticleDispatcherImpl
import com.example.newsapplinktask.model.retrofit.Service
import com.example.newsapplinktask.repository.remote.article.ArticleRemoteRepo
import com.example.newsapplinktask.repository.remote.article.ArticleRemoteRepoImpl
import com.example.newsapplinktask.viewmodel.article.ArticleViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module



val networkModule = module {
    single { Service.getService() }
}


val article = module {
    viewModel { ArticleViewModel(get()) }
    factory<ArticleDispatcher> { ArticleDispatcherImpl(get()) }
    factory<ArticleRemoteRepo> { ArticleRemoteRepoImpl(get()) }
}
