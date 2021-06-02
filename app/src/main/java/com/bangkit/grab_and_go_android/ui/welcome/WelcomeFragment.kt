package com.bangkit.grab_and_go_android.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            goToSignIn()
        }
        binding.btnSignUp.setOnClickListener {
            goToCreateAccount()
        }

    }

    private fun goToSignIn() {
        findNavController().navigate(
            WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment()
        )
    }

    private fun goToCreateAccount() {
        findNavController().navigate(
            WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment()
        )
    }
}