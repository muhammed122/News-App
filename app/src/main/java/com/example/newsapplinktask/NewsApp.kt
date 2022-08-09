package com.example.newsapplinktask

import android.app.Application
import android.content.Context
import java.lang.ref.WeakReference

class NewsApp :Application() {
    companion object {

        private var context: WeakReference<Context>? = null
        fun getContext(): Context? = context?.get()
    }
}