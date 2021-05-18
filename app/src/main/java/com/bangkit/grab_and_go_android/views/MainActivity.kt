package com.bangkit.grab_and_go_android.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.databinding.ActivityMainBinding
import com.bangkit.grab_and_go_android.extensions.Extensions.toast
import com.bangkit.grab_and_go_android.utils.FirebaseUtils.firebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        binding.btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, CreateAccountActivity::class.java))
            toast("signed out")
            finish()
        }
    }
}