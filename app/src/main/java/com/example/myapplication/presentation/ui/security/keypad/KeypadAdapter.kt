package com.example.myapplication.presentation.ui.security.keypad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ButtonItemBinding
import com.example.myapplication.presentation.utils.hide
import com.example.myapplication.presentation.utils.show

class KeypadAdapter(
    private val onClick: (KeypadItem) -> Unit
) : RecyclerView.Adapter<KeypadAdapter.ViewHolder>() {

    private val items = listOf(
        KeypadItem.Number(1), KeypadItem.Number(2), KeypadItem.Number(3),
        KeypadItem.Number(4), KeypadItem.Number(5), KeypadItem.Number(6),
        KeypadItem.Number(7), KeypadItem.Number(8), KeypadItem.Number(9),
        KeypadItem.Fingerprint, KeypadItem.Number(0), KeypadItem.Delete
    )

    inner class ViewHolder(val binding: ButtonItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ButtonItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            when (item) {
                is KeypadItem.Number -> {
                    tvNumber.show()
                    imgIcon.hide()
                    tvNumber.text = item.value.toString()
                }
                KeypadItem.Fingerprint -> {
                    tvNumber.hide()
                    imgIcon.show()
                    imgIcon.setImageResource(R.drawable.ic_fingerprint)
                }
                KeypadItem.Delete -> {
                    tvNumber.hide()
                    imgIcon.show()
                    imgIcon.setImageResource(R.drawable.ic_delete)
                }
            }

            root.setOnClickListener { onClick(item) }
        }
    }

    override fun getItemCount() = items.size
}