package com.example.myapplication.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemOrderBinding

class OrderAdapter(
    private val onReviewClick: (Order) -> Unit,
    private val onBuyAgainClick: (Order) -> Unit
) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.apply {
                ivProduct.setImageResource(order.imageRes)
                tvOrderTitle.text = order.title
                tvPrice.text = order.price
                binding.tvQuantity.text = binding.root.context.getString(R.string.order_quantity, order.quantity)
                tvColor.text = order.color
                tvStatus.text = order.status.name

                if (order.status != OrderStatus.COMPLETED) {
                    btnAction.visibility = View.GONE
                    return
                }

                btnAction.visibility = View.VISIBLE

                if (order.reviewed) {
                    btnAction.text = binding.root.context.getString(R.string.buy_again_1)
                    btnAction.setOnClickListener { onBuyAgainClick(order) }
                } else {
                    btnAction.text = binding.root.context.getString(R.string.leave_review_1)
                    btnAction.setOnClickListener { onReviewClick(order) }
                }
            }
        }
    }

    class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}