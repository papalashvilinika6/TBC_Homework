package com.example.myapplication.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentOrdersBinding
import com.example.myapplication.dialog.ReviewBottomSheetDialogFragment
import com.example.myapplication.order_list.OrderListViewModel
import com.example.myapplication.order_list.OrdersViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private val viewModel: OrderListViewModel by activityViewModels()
    private lateinit var viewPagerAdapter: OrdersViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupTabs()
        observeReviewEvents()
    }

    override fun listeners() {

    }

    override fun bind() {
        setupViewPager()
        setupTabs()
        observeReviewEvents()
    }

    private fun setupViewPager() {
        viewPagerAdapter = OrdersViewPagerAdapter(requireActivity()) { order ->
            showReviewBottomSheet(order)
        }
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = true
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Active" else "Completed"
        }.attach()
    }

    private fun observeReviewEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.openReviewSheet.collect { order ->
                    showReviewBottomSheet(order)
                }
            }
        }
    }

    private fun showReviewBottomSheet(order: Order) {
        val bottomSheet = ReviewBottomSheetDialogFragment.Companion.newInstance(order)
        bottomSheet.setOnReviewSubmitListener { submittedOrder, rating, reviewText ->
            viewModel.leaveReview(submittedOrder)
        }
        bottomSheet.show(childFragmentManager, ReviewBottomSheetDialogFragment::class.java.simpleName)
    }
}