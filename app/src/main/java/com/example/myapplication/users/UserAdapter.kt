package com.example.myapplication.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemUserBinding

class UsersAdapter(
    private val users: List<User>,
    private val onItemClick: (User) -> Unit
) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvFullName.text = "${user.firstName} ${user.lastName}"
            binding.tvAge.text = "Age: ${user.age}"
            binding.tvEmail.text = user.email

            binding.root.setOnLongClickListener {
                onItemClick(user)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}


