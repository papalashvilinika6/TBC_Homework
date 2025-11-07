package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.field.Field
import com.example.myapplication.field.FieldType
import com.example.myapplication.databinding.ItemChooserBinding
import com.example.myapplication.databinding.ItemInputBinding

class FieldAdapter(private val fields: List<Field>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_INPUT = 0
    private val TYPE_CHOOSER = 1

    override fun getItemViewType(position: Int): Int {
        return when (fields[position].fieldType) {
            FieldType.INPUT -> TYPE_INPUT
            FieldType.CHOOSER -> TYPE_CHOOSER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_INPUT) {
            val binding = ItemInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            InputViewHolder(binding)
        } else {
            val binding = ItemChooserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChooserViewHolder(binding)
        }
    }

    override fun getItemCount() = fields.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val field = fields[position]
        when (holder) {
            is InputViewHolder -> holder.bind(field)
            is ChooserViewHolder -> holder.bind(field)
        }
    }

    inner class InputViewHolder(private val binding: ItemInputBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(field: Field) {
            binding.editTextField.hint = field.hint
            Glide.with(binding.icon).load(field.icon).into(binding.icon)
        }
    }

    inner class ChooserViewHolder(private val binding: ItemChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(field: Field) {
            binding.textViewHint.text = field.hint
            Glide.with(binding.icon).load(field.icon).into(binding.icon)
        }
    }
}