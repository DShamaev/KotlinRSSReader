package com.dmitriyshamaev.kotlinreader.adapters

import android.databinding.BindingAdapter
import android.view.View

object BindingAdapters {

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        if (show) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}