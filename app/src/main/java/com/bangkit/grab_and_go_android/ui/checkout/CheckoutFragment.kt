package com.bangkit.grab_and_go_android.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.databinding.FragmentCheckoutBinding
import com.bangkit.grab_and_go_android.utils.Constants
import com.bangkit.grab_and_go_android.utils.Util.toRupiahString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private val viewModel by viewModels<CheckoutViewModel>()
    private val args: CheckoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val cart = args.cart
        val total = args.total
        binding.tvTotalPayment.text = total.toInt().toRupiahString()

        binding.btnDone.setOnClickListener {
//            findNavController().setGraph(R.navigation.nav_graph)
            val request = NavDeepLinkRequest.Builder
                .fromUri("grabngoapp://main/home".toUri())
                .build()
            findNavController().navigate(request)
        }

    }

}