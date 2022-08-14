package com.example.newsapplinktask.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import com.example.newsapplinktask.model.response.ErrorScreen
import com.example.newsapplinktask.view.util.BaseView
import com.example.newsapplinktask.viewmodel.BaseViewModel

abstract class BasicFragment : BaseFragment(), BaseView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()?.showFullLoading?.observe(this, showFullLoading)
        getViewModel()?.errorDialog?.observe(this, showErrorUi)
    }

    override fun showError(error: ErrorScreen) {
        if (error.message != null)
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()

    }

    abstract fun getViewModel(): BaseViewModel?

    private val showFullLoading = Observer<Boolean> {
        if (it)
            showLoading()
        else
            hideLoading()
    }



    private val showErrorUi = Observer<ErrorScreen> { showError(it) }

    override fun onDestroy() {
        super.onDestroy()
        getViewModel()?.clear()
    }


    fun onBackPressed() {
        val callback = object : OnBackPressedCallback(
            true
            /** true means that the callback is enabled */
        ) {
            override fun handleOnBackPressed() {
                if (requireActivity().supportFragmentManager.backStackEntryCount > 0)
                    requireActivity().supportFragmentManager.popBackStack()
                else
                    activity?.finish()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }




    fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}