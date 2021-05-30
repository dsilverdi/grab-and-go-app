package com.bangkit.grab_and_go_android.utils

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

object ActivityExt {
    fun Activity.toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}