package com.bangkit.grab_and_go_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customButtonLayout.btnHistory.setOnClickListener {
            goToHistory()
        }
        binding.customButtonLayout.btnHistoryArrow.setOnClickListener {
            goToHistory()
        }

        binding.btnUserSettings.setOnClickListener {
            goToProfile()
        }

        binding.startShoppingFab.setOnClickListener {
            goToShopping()
        }
    }

    private fun goToShopping() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCameraFragment())
    }

    private fun goToProfile() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment())
    }

    private fun goToHistory() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
    }

}