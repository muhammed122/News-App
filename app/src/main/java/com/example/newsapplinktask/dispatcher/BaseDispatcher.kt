package com.example.newsapplinktask.dispatcher

import com.example.newsapplinktask.model.Constant
import com.example.newsapplinktask.model.request.BaseRequestFactory
import com.example.newsapplinktask.model.response.BaseModel
import com.example.newsapplinktask.model.response.ResponseException
import com.example.newsapplinktask.repository.BaseRemoteRepo
import com.example.newsapplinktask.util.network.NetworkUtil
import java.lang.reflect.Type

interface BaseDispatcher {
    val remoteRepo: BaseRemoteRepo

    suspend fun fetchData( type: Type, requestFactory: BaseRequestFactory): Any? {
        var errorMessage: String? = null
        var responseCode: String? = null
        val response = try {
            val isNetworkConnected = NetworkUtil.isNetworkAvailable()
            if (isNetworkConnected) {
                requestFactory.baseRequestParam.apiKey = Constant.API_KEY
                remoteRepo.fetchData(requestFactory)

            } else {
                errorMessage = NetworkUtil.NETWORK_ERROR_MSG
                responseCode = NetworkUtil.NO_INTERNET_CONNECTION_CODE
                null
            }
        } catch (ex: Exception) {
            errorMessage = NetworkUtil.CLIENT_ERROR_MSG
            responseCode = ""
            ex.printStackTrace()
            null
        }

        if (response != null && response.isSuccessful) {
            val body = response.body()
            if (body is BaseModel) {
                if (body.status == "ok") {

                    return response.body()
                } else {
                    errorMessage = body.message
                    responseCode = "" + body.code
                }
            }

        } else {
            try {
                if (response?.errorBody() != null) {
                    errorMessage = response.message()
                    responseCode = "" + response.code()
                }

            } catch (e: Exception) {

            }

            if (errorMessage == null  || response?.code() == 401) {
                errorMessage = NetworkUtil.SERVER_ERROR_MSG
                responseCode = "" + response?.code()
            }
        }

        throw ResponseException(
            message = errorMessage,
            responseCode = responseCode,
            endPoint = requestFactory.getEndPoint()
        )
    }



}