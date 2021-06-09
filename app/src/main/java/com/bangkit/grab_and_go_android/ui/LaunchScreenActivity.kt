package com.bangkit.grab_and_go_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LaunchScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Send user to MainActivity as soon as this activity loads
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        // remove this activity from the stack
        finish()
    }

}
