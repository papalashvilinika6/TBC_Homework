package com.example.myapplication.dialog

import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.RatingBar
import com.example.myapplication.R
import com.example.myapplication.databinding.ReviewBottomSheetBinding
import com.example.myapplication.order.Order
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: ReviewBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var order: Order? = null
    private var onReviewSubmit: ((Order, Float, String) -> Unit)? = null

    companion object {
        private const val ARG_ORDER = "order"

        fun newInstance(order: Order): ReviewBottomSheetDialogFragment {
            return ReviewBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ORDER, order)
                }
            }
        }
    }

    fun setOnReviewSubmitListener(listener: (Order, Float, String) -> Unit) {
        onReviewSubmit = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable(ARG_ORDER)
        }
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )?.setBackgroundResource(android.R.color.transparent)
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReviewBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
        }

        animateEntrance()

        order?.let { currentOrder ->
            binding.apply {
                ivProduct.setImageResource(currentOrder.imageRes)
                tvProductTitle.text = currentOrder.title
                binding.tvProductDetails.text =
                    binding.root.context.getString(R.string.product_details, currentOrder.color, currentOrder.quantity)

                tvProductPrice.text = currentOrder.price
                tvProductStatus.text = currentOrder.status.name

                val ratingBarView = binding.root.findViewById<RatingBar>(R.id.ratingBar)
                ratingBarView?.rating = 4f

                btnSubmitReview.setOnClickListener {
                    val rating = ratingBarView?.rating ?: 0f
                    val reviewText = etReview.text.toString().trim()

                    if (reviewText.isNotEmpty()) {
                        onReviewSubmit?.invoke(currentOrder, rating, reviewText)
                        dismiss()
                    }
                }

                btnSubmitReview.setOnClickListener { dismiss() }
            }
        }

    }

    private fun animateEntrance() {
        binding.layoutReview.alpha = 0f
        binding.layoutReview.translationY = 500f
        binding.layoutReview.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(300)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}