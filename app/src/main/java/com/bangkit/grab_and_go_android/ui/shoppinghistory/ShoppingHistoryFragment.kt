package com.bangkit.grab_and_go_android.ui.shoppinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.grab_and_go_android.databinding.FragmentShoppingHistoryBinding

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