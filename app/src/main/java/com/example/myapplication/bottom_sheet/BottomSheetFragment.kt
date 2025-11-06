package com.example.myapplication.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.card.CardViewModel
import com.example.myapplication.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.activityViewModels

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnYes()
        btnNo()
    }

    private fun btnYes() {
        val cardId = arguments?.getInt("cardId") ?: return

        binding.btnYes.setOnClickListener {
            viewModel.deleteCard(cardId)
            dismiss()
        }
    }

    private fun btnNo() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(cardId: Int): BottomSheetFragment {
            val fragment = BottomSheetFragment()
            fragment.arguments = Bundle().apply { putInt("cardId", cardId) }
            return fragment
        }
    }
}

