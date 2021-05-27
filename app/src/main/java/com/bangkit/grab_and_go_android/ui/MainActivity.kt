package com.bangkit.grab_and_go_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.grab_and_go_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnSignOut.setOnClickListener {
//            firebaseAuth.signOut()
//            startActivity(Intent(this, CreateAccountActivity::class.java))
//            toast("signed out")
//            finish()
//        }
    }
}