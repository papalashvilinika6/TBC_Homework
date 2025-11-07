package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.field.Field
import com.example.myapplication.databinding.ItemGroupBinding

class GroupAdapter(private val groups: List<List<Field>>) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupFields = groups[position]

        val fieldAdapter = FieldAdapter(groupFields)
        holder.binding.innerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fieldAdapter
        }
    }

    override fun getItemCount() = groups.size
}