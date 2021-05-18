package com.bangkit.grab_and_go_android.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.bangkit.grab_and_go_android.databinding.ActivitySignInBinding
import com.bangkit.grab_and_go_android.extensions.Extensions.toast
import com.bangkit.grab_and_go_android.utils.FirebaseUtils.firebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        signInInputsArray = arrayOf(binding.etSignInEmail, binding.etSignInPassword)
        binding.btnCreateAccount2.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        binding.btnSignIn.setOnClickListener {
            signInUser()
        }
    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser() {
        signInEmail = binding.etSignInEmail.text.toString().trim()
        signInPassword = binding.etSignInPassword.text.toString().trim()

        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        toast("signed in successfully")
                        finish()
                    } else {
                        toast("sign in failed")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}