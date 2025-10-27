package com.example.myapplication.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemAddressBinding

class AddressAdapter(private val viewModel: AddressViewModel, private val onItemClick: (Address) -> Unit, private val onItemLongClick: (Address) -> Unit) :
    ListAdapter<Address, AddressAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Address) {
            binding.apply {
                tvTitle.text = item.title
                tvSubtitle.text = item.subtitle
                imgType.setImageResource(item.typeIcon)

                radioSelect.isChecked = item.isSelected

                radioSelect.setOnClickListener {
                    viewModel.selectItem(item.id)
                }

                tvEdit.setOnClickListener {
                    if (item.isSelected) onItemClick(item)
                }

                root.setOnLongClickListener {
                    onItemLongClick(item)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address) = oldItem.subtitle == newItem.subtitle
        override fun areContentsTheSame(oldItem: Address, newItem: Address) = oldItem == newItem
    }
}