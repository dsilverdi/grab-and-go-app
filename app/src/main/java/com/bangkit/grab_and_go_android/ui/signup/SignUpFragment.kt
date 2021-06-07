package com.bangkit.grab_and_go_android.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.databinding.FragmentSignUpBinding
import com.bangkit.grab_and_go_android.utils.hideKeyboard
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

        viewModel.signUp(
            User(
                uid = null,
                displayName = null,
                email = email,
                password = password
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUI()

        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            if(loading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        })

        // Create account doesn;t have email verification feature for now

        viewModel.success.observe(viewLifecycleOwner, { success ->
            if(success) {
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                )
//                setSuccessText()
//                toastLong("Verification email has been sent")
            }
        })
        viewModel.errorException.observe(viewLifecycleOwner, { exception ->
            if(exception != null) {
                setFailText(exception.message.toString())
//                toastLong("Error "+ exception.message.toString())
//                Log.e("SignUp", "Error: $exception")
            }
        })

        binding.btnCreateAccount.setOnClickListener {
            hideKeyboard(view)
            signUpUser()
        }

    }

    private fun setUpUI() {
        binding.tvSignupResult.text = ""
    }

    private fun setFailText(error: String) {
        binding.tvSignupResult.setTextColor(Color.GREEN)
        binding.tvSignupResult.text = error
    }

    private fun setSuccessText() {
        binding.tvSignupResult.setTextColor(Color.BLACK)
        binding.tvSignupResult.text = getString(R.string.sign_up_success)
    }

    private fun showProgressBar() {
        binding.signUpProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.signUpProgressBar.visibility = View.GONE
    }

}