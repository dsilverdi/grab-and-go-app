package com.bangkit.grab_and_go_android.ui.shoppinghistory

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.databinding.FragmentShoppingHistoryBinding
import com.bangkit.grab_and_go_android.ui.MainActivity
import com.google.android.material.navigation.NavigationView

class ShoppingHistoryFragment : Fragment() {

    private lateinit var binding: FragmentShoppingHistoryBinding
//    private val viewModel by viewModels<ShoppingHistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}