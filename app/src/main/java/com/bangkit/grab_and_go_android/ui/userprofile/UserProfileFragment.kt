package com.bangkit.grab_and_go_android.ui.userprofile

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.User
import com.bangkit.grab_and_go_android.databinding.FragmentUserProfileBinding
import com.bangkit.grab_and_go_android.utils.setUpProgressBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel by viewModels<UserProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
//        toolbar.navigationIcon?.setTint(Color.BLACK)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })

        setUpProgressBar(binding.profileProgressBar, viewModel.loading)

        viewModel.getSignedInUser().observe(viewLifecycleOwner, { user ->
            if(user != null) {
                setUserDetailUI(user)
            }
        })

        viewModel.logoutSuccess.observe(viewLifecycleOwner, { success ->
            if(success) {
                findNavController().navigate(
                    UserProfileFragmentDirections.actionUserProfileFragmentToWelcomeFragment()
                )
            }
        })
        binding.btnLogout.setOnClickListener {
            showPopup()
        }

    }

    private fun setUserDetailUI(user: User) {
        binding.tvUserEmail.text = user.email
        binding.tvUserName.text = user.name
    }

    private fun showPopup() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        alert.setMessage("Are you sure?")
            .setPositiveButton("Logout", DialogInterface.OnClickListener { dialog, which ->
                signOutUser() // Last step. Logout function
            }).setNegativeButton("Cancel", null)
        val alert1: AlertDialog = alert.create()
        alert1.show()
    }

    private fun signOutUser() {
        viewModel.signOut()
    }

}