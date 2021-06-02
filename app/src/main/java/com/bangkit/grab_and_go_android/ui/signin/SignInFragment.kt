package com.bangkit.grab_and_go_android.ui.signin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.databinding.FragmentSignInBinding
import com.bangkit.grab_and_go_android.utils.setUpProgressBar
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpProgressBar(binding.signInProgressBar, viewModel.loading)

        viewModel.getSignedInUser()

        viewModel.currentUser.observe(viewLifecycleOwner, { user ->
            if(user != null) {
//                toastShort("Signed in successfully")
//                Log.d(TAG, Thread.currentThread().toString())
//                Log.d(TAG, viewModel.currentUser.value.toString())
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
            }
        })
        viewModel.loginFailed.observe(viewLifecycleOwner, { failed ->
            if(failed) {
                toastShort("Sign In Failed")
                Log.d(TAG, "SIGN IN FAILED")
            }
        })

        binding.btnSignIn.setOnClickListener {
            hideKeyboardFrom(binding.etSignInEmail)
            hideKeyboardFrom(binding.etSignInPassword)
            signInUser()
        }

    }

    private fun hideKeyboardFrom(view: View) {
        val context = requireActivity()
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
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
}