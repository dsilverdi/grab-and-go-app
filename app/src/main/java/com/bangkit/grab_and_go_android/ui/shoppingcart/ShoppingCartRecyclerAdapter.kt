package com.bangkit.grab_and_go_android.ui.shoppingcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.Product

class ShoppingCartRecyclerAdapter : RecyclerView.Adapter<ShoppingCartRecyclerAdapter.ViewHolder>() {

    private var listCartProduct = arrayListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return listCartProduct.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            TODO("Not yet implemented")
        }

    }
}