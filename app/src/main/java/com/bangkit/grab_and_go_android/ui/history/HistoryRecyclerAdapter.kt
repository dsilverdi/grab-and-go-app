package com.bangkit.grab_and_go_android.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.databinding.ItemHistoryBinding

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>() {

    private var listShoppingHistory = arrayListOf<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listShoppingHistory[position])
    }

    override fun getItemCount(): Int {
        return listShoppingHistory.size
    }

    fun setData(list: Collection<Cart>) {
        listShoppingHistory.clear()
        listShoppingHistory.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryBinding.bind(itemView)
        fun bind(cart: Cart) {
            binding.tvTotal.text = cart.getTotalAsString()
            binding.tvDatetime.text = cart.getDate()
        }

    }
}