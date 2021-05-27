package com.bangkit.grab_and_go_android.utils

import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastShort(msg: String){
    val toast = Toast.makeText(this.activity, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP, 0, 75)
    toast.show()
}
fun Fragment.toastLong(msg: String){
    val toast = Toast.makeText(this.activity, msg, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.TOP, 0, 75)
    toast.show()
}
//fun Fragment.toastTop(msg: String){
//    val toast = Toast.makeText(this.activity, msg, Toast.LENGTH_SHORT)
//    toast.setGravity(Gravity.TOP, 0, 0)
//    toast.show()
//}