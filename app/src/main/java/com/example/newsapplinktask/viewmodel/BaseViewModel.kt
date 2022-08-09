package com.example.newsapplinktask.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapplinktask.dispatcher.BaseDispatcher
import com.example.newsapplinktask.model.request.BaseRequestFactory
import com.example.newsapplinktask.model.response.BaseModel
import com.example.newsapplinktask.model.response.ErrorScreen
import com.example.newsapplinktask.model.response.ResponseException

import com.qpn.kamashka.util.livedata.SingleLiveEvent
import kotlinx.coroutines.*
import java.lang.reflect.Type

abstract class BaseViewModel : ViewModel() {

    var errorDialog: SingleLiveEvent<ErrorScreen> = SingleLiveEvent()
    var showFullLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var showSuccessDialog: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private var connectJob: Job? = null





    fun fetchData(
        cash: Boolean, type: Type, requestFactory: BaseRequestFactory, showLoading: Boolean = true,
        showSuccess: Boolean = false, showError: Boolean = true,
        proceedResponse: (
            t: Any?
        ) -> Unit
    ) {


        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            if (throwable is ResponseException) {
                onError(
                    ErrorScreen(
                        throwable.message,
                        throwable.responseCode,
                        requestFactory.getEndPoint(),
                        requestFactory.baseRequestParam
                    )
                )
            }
        }
        fetchLogic(
            showLoading,
            showSuccess,
            showError,
            exceptionHandler,
            cash,
            type,
            requestFactory,
            proceedResponse
        )
    }

    private fun fetchLogic(
        showLoading: Boolean,
        showSuccess: Boolean,
        showError: Boolean,
        exceptionHandler: CoroutineExceptionHandler,
        cash: Boolean,
        type: Type,
        requestFactory: BaseRequestFactory,
        proceedResponse: (t: Any?) -> Unit
    ) {
        if (showLoading)
            showFullLoading.value = true
        connectJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = getDispatcher()?.fetchData(cash, type, requestFactory)
            withContext(Dispatchers.Main) {
                proceedResponse(response)
                if (showLoading)
                    showFullLoading.value = false

                if (response is BaseModel) {
                    if (response.status == "ok" && showSuccess)
                        showSuccessDialog.value = true

                }


            }
        }
    }

    private fun onError(errorScreen: ErrorScreen) {
        errorDialog.postValue(errorScreen)
        showFullLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        connectJob?.cancel()
    }

    fun clear() {
        onCleared()
    }

    abstract fun getDispatcher(): BaseDispatcher?



}