package com.bangkit.grab_and_go_android.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.databinding.FragmentSignUpBinding
import com.bangkit.grab_and_go_android.utils.toastLong
import com.bangkit.grab_and_go_android.utils.toastShort
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun signUpUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if(email.isBlank() && password.isBlank()) return
        if(password != confirmPassword){
            toastShort("Passwords don't match")
            return
        }

        viewModel.verificationEmailSent.observe(viewLifecycleOwner, { isSent ->
            if(isSent) {
                toastLong("Verification email has been sent")
            }
        })
        viewModel.signUp(
            User(null, email, password)
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            if(loading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        })

        binding.btnCreateAccount.setOnClickListener {
            signUpUser()
        }

    }

    private fun showProgressBar() {
        binding.signUpProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.signUpProgressBar.visibility = View.GONE
    }

}