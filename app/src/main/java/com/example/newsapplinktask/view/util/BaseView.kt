package com.example.newsapplinktask.view.util

import com.example.newsapplinktask.model.response.ErrorScreen


interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun showError(error: ErrorScreen)
}