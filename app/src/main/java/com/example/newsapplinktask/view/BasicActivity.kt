package com.example.newsapplinktask.view

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.newsapplinktask.model.response.ErrorScreen

import com.example.newsapplinktask.view.util.BaseView
import com.example.newsapplinktask.viewmodel.BaseViewModel

abstract class BasicActivity : BaseActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()?.showFullLoading?.observe(this, showFullLoading)
        getViewModel()?.errorDialog?.observe(this, showErrorUi)
    }

    private val showFullLoading = Observer<Boolean> {
        if (it)
            showLoading()
        else
            hideLoading()
    }

    private val showErrorUi = Observer<ErrorScreen> {
        showError(it)
    }



    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(error: ErrorScreen) {
        if (error.message != null)
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()

    }

    abstract fun getViewModel(): BaseViewModel?

    override fun onDestroy() {
        super.onDestroy()
        getViewModel()?.clear()
    }




}
