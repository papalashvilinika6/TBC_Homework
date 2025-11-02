package com.example.myapplication.order_list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.order.Order
import com.example.myapplication.order.OrderStatus

class OrdersViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onReviewClick: (Order) -> Unit
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val status = if (position == 0) OrderStatus.ACTIVE else OrderStatus.COMPLETED
        return OrderListFragment.newInstance(status).apply {
            setOnReviewClickListener(onReviewClick)
        }
    }
}

