package com.example.newsapplinktask.view

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity != null && activity is BaseActivity && requireActivity().supportFragmentManager
                .backStackEntryCount > 0
        ) {
            (activity as BaseActivity).notifyFragmentsAppearing()
        }
    }

    open fun didAppear() {}

    @Suppress("DEPRECATION")
    fun getScreenWidth(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity?.windowManager?.currentWindowMetrics
            val insets: Insets =
                windowMetrics?.windowInsets!!.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun addFragment(
        fragment: Fragment, container: Int, addToBackStack: Boolean,
        tag: String = "", bundle: Bundle? = null
    ) {
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        if (addToBackStack)
            ft.addToBackStack(tag)
        if (bundle != null)
            fragment.arguments = bundle
        ft.add(container, fragment, tag)
        ft.commit()
    }
}