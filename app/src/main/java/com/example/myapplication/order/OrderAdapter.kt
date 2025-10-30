package com.example.myapplication.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemOrderBinding
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderAdapter(
    private val onItemClick: (Order) -> Unit
) : ListAdapter<Order, OrderAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Order) = with(binding){
            tvOrderId.text = root.context.getString(R.string.order, item.id)

            val df = SimpleDateFormat(root.context.getString(R.string.dd_mm_yyyy), Locale.getDefault())
            tvDate.text = df.format(Date(item.dateMillis))

            tvTrackingNumber.text = item.trackingNumber
            tvQuantity.text = item.quantity.toString()

            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            binding.tvSubtotalValue.text = currencyFormat.format(item.subtotal)

            binding.tvStatus.text = item.status

            val context = binding.root.context
            val statusColorRes = when (item.status) {
                "PENDING" -> R.color.yellow
                "DELIVERED" -> R.color.green
                "CANCELED" -> R.color.red
                else -> R.color.black
            }
            tvStatus.setTextColor(ContextCompat.getColor(context, statusColorRes))

            val isPending = item.status == "PENDING"
            btnDetails.isEnabled = isPending
            btnDetails.alpha = if (isPending) 1f else 0.5f
            btnDetails.setOnClickListener {
                if (isPending) onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
    }
}