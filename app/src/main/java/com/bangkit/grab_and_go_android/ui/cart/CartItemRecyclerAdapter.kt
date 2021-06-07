package com.bangkit.grab_and_go_android.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.CartItem
import com.bangkit.grab_and_go_android.databinding.ItemCartBinding

class CartItemRecyclerAdapter : RecyclerView.Adapter<CartItemRecyclerAdapter.ViewHolder>() {

    private var listCartItem = arrayListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCartItem[position])
    }

    override fun getItemCount(): Int {
        return listCartItem.size
    }

    fun setData(list: Collection<CartItem>) {
        listCartItem.clear()
        listCartItem.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCartBinding.bind(itemView)
        fun bind(cartItem: CartItem) {
            binding.tvItemProductName.text = cartItem.name
            binding.tvItemQty.text = cartItem.getQtyAsString()
            binding.tvItemSubtotal.text = cartItem.getSubtotalAsString()
        }

    }
}