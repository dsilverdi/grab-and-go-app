package com.bangkit.grab_and_go_android.ui.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.databinding.FragmentCartBinding
import com.bangkit.grab_and_go_android.ui.camera.ImageData
import com.bangkit.grab_and_go_android.utils.Util
import com.bangkit.grab_and_go_android.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class CartFragment : Fragment() {

    companion object {
        const val TAG = "ShoppingCartFragment"
//        const val CART_ARG = "cart"
        const val IMAGE_BYTE_ARRAY_ARG = "img"
//        const val IMAGE_DATA_ARG = "img_data"
    }

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var rvAdapter: CartItemRecyclerAdapter
//    private var cart: Cart? = null
    private var imgByteArray: ByteArray? = null
    private var imageData: ImageData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            cart = it.getParcelable<Cart>(CART_ARG)
            imgByteArray = it.getByteArray(IMAGE_BYTE_ARRAY_ARG)
//            imageData = it.getParcelable(IMAGE_DATA_ARG)

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

        if(imgByteArray == null) {
            toastLong("Error image is null")
            return
        } else {
            imgByteArray?.let {
                viewModel.getCartItems(it)
            }
        }

        val toolbar: Toolbar = binding.toolbar
        toolbar.setNavigationIcon(Util.getBackButtonIconRes())
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
//        Log.d(TAG, cart.toString())

        setupRecyclerView()

        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            if(loading) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        viewModel.cart.observe(viewLifecycleOwner, { cart ->
            if(cart != null) {
                binding.tvTotal.text = cart.getTotalAsString()
                rvAdapter.setData(cart.listCartItem)
                showUI(cart.listCartItem.isEmpty())
            }
        })


        binding.btnCheckout.setOnClickListener {
            viewModel.checkout()
        }

        viewModel.transaction.observe(viewLifecycleOwner, { transaction ->
//            val url = URL(transaction.midtransUrl!!)
            val uri = Uri.parse(transaction.midtransUrl!!) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
    }

    private fun checkout() {

    }

    private fun setupRecyclerView() {
        rvAdapter = CartItemRecyclerAdapter()
        binding.rvCart.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun showUI(isEmpty: Boolean) {
        if(isEmpty) {
            binding.tvEmptyCart.visibility = View.VISIBLE
            binding.btnCheckout.isEnabled = false
        } else {
            binding.tvTotalText.visibility = View.VISIBLE
            binding.tvTotal.visibility = View.VISIBLE
            binding.btnCheckout.visibility = View.VISIBLE
            binding.btnBack.visibility = View.VISIBLE
        }

    }

    private fun showLoading() {
        binding.tvEmptyCart.visibility = View.GONE
        binding.tvTotalText.visibility = View.GONE
        binding.tvTotal.visibility = View.GONE
        binding.btnCheckout.visibility = View.GONE
        binding.btnBack.visibility = View.GONE

        binding.cartProgressBar.visibility = View.VISIBLE
        binding.tvLoadingText.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.cartProgressBar.visibility = View.GONE
        binding.tvLoadingText.visibility = View.GONE
    }

}