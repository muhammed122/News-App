package com.example.newsapplinktask.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {



    fun notifyFragmentsAppearing() {
        val currentFragment: Fragment? = getCurrentFragment()
        if (currentFragment is BaseFragment) {
            currentFragment.didAppear()
        }
    }

    open fun getCurrentFragment(): Fragment? {
        return if (getContainerId() != -1)
            supportFragmentManager
                .findFragmentById(getContainerId()) else null
    }

    open fun getContainerId(): Int = -1
}