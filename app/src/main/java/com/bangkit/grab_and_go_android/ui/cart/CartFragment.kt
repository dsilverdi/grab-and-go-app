package com.bangkit.grab_and_go_android.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.databinding.FragmentCartBinding
import com.bangkit.grab_and_go_android.ui.camera.ImageData

class CartFragment : Fragment() {

    companion object {
        const val TAG = "ShoppingCartFragment"
        const val CART_ARG = "cart"
        const val IMAGE_BYTE_ARRAY_ARG = "img"
        const val IMAGE_DATA_ARG = "img_data"
    }

    private lateinit var binding: FragmentCartBinding
//    private val viewModel: ShoppingCartViewModel by viewModels()
    private lateinit var rvAdapter: CartItemRecyclerAdapter
    private var cart: Cart? = null
    private var imgByteArray: ByteArray? = null
    private var imageData: ImageData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cart = it.getParcelable<Cart>(CART_ARG)
            imgByteArray = it.getByteArray(IMAGE_BYTE_ARRAY_ARG)
            imageData = it.getParcelable(IMAGE_DATA_ARG)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        Log.d(TAG, cart.toString())
        setupRecyclerView()
        binding.tvTotal.text = cart?.getTotalAsString()
    }

    private fun setupRecyclerView() {
        rvAdapter = CartItemRecyclerAdapter()
        binding.rvCart.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        cart?.let {
            rvAdapter.setData(it.listCartItem)
        }
    }

}