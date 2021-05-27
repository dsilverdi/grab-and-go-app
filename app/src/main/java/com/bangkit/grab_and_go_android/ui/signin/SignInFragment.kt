package com.bangkit.grab_and_go_android.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.databinding.FragmentSignInBinding
import com.bangkit.grab_and_go_android.utils.toastShort
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment() {

    companion object {
        const val TAG = "SignInFragment"
    }
    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun signInUser() {
        val email = binding.etSignInEmail.text.toString().trim()
        val password = binding.etSignInPassword.text.toString().trim()

        if(email.isBlank() && password.isBlank()) {
            toastShort("Email or password must not be blank")
            return
        }

        viewModel.authenticate(email, password)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSignedInUser().observe(viewLifecycleOwner, { user ->
//            if(user != null) {
//                Log.d(TAG, user.toString())
//                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
//            } else {
//                Log.d(TAG, "Null user")
//            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            if(loading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        })

        viewModel.loginSuccess.observe(viewLifecycleOwner, { success ->
            if(success) {
                toastShort("Signed in successfully")
                Log.d(TAG, Thread.currentThread().toString())
                Log.d(TAG, viewModel.currentUser.value.toString())
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
            }
        })
        viewModel.loginFailed.observe(viewLifecycleOwner, { failed ->
            if(failed) {
                toastShort("Sign In Failed")
                Log.d(TAG, "SIGN IN FAILED")
            }
        })

        binding.btnNavigateCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.btnSignIn.setOnClickListener {
            signInUser()
        }

    }

    private fun showProgressBar() {
        binding.signInProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.signInProgressBar.visibility = View.GONE
    }

}