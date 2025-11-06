package com.example.myapplication.payment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.bottom_sheet.BottomSheetFragment
import com.example.myapplication.card.CardAdapter
import com.example.myapplication.card.CardViewModel
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentPaymentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private val viewModel: CardViewModel by activityViewModels()
    private lateinit var adapter: CardAdapter

    override fun bind() {
        setupAdapter()
    }

    override fun listeners() {
        setupListeners()
    }

    override fun observers() {
        setupObservers()
    }

    private fun setupAdapter() {
        adapter = CardAdapter { card ->
            showDeleteBottomSheet(card.id)
        }
        binding.vpCardsId.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnNewId.setOnClickListener {
            navigateToAddNewCard()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cards.collectLatest { cards ->
                adapter.submitList(cards)
            }
        }
    }

    private fun navigateToAddNewCard() {
        findNavController().navigate(R.id.action_paymentFragment_to_addNewFragment)
    }

    private fun showDeleteBottomSheet(cardId: Int) {
        val bottomSheet = BottomSheetFragment.newInstance(cardId)
        bottomSheet.show(parentFragmentManager, "deleteSheet")
    }
}

