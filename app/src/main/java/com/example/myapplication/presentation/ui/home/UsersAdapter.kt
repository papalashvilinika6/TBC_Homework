package com.example.myapplication.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.domain.model.User
import com.example.myapplication.R

class UsersAdapter :
    ListAdapter<User, UsersAdapter.UserViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(a: User, b: User) = a.id == b.id
        override fun areContentsTheSame(a: User, b: User) = a == b
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvName.text = item.fullName
            tvEmail.text = item.email
            tvActivation.text = item.lastActiveDescription

            imgAvatar.load(item.profileImageUrl) {
                crossfade(true)
                fallback(R.drawable.user)
                error(R.drawable.loading)
                transformations(CircleCropTransformation())
            }
        }
    }
}
