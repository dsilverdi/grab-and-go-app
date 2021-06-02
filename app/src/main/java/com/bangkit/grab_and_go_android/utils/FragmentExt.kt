package com.bangkit.grab_and_go_android.utils

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData


fun Fragment.toastShort(msg: String){
    val toast = Toast.makeText(this.activity, msg, Toast.LENGTH_SHORT)
    // DOes nothing in API 30
    toast.setGravity(Gravity.TOP, 0, 75)
    toast.show()
}
fun Fragment.toastLong(msg: String){
    val toast = Toast.makeText(this.activity, msg, Toast.LENGTH_LONG)
    // DOes nothing in API 30
    toast.setGravity(Gravity.TOP, 0, 75)
    toast.show()
}

fun Fragment.setUpProgressBar(progressBar: ProgressBar, loadingState: LiveData<Boolean>, ) {
    loadingState.observe(viewLifecycleOwner, { loading ->
        if(loading) {
            showProgressBar(progressBar)
        } else {
            hideProgressBar(progressBar)
        }
    })
}

private fun showProgressBar(progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
}

private fun hideProgressBar(progressBar: ProgressBar) {
    progressBar.visibility = View.GONE
}
