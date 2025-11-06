package com.example.myapplication.add_new

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.card.Card
import com.example.myapplication.card.CardViewModel
import com.example.myapplication.card.Type
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentAddNewBinding
import com.google.android.material.snackbar.Snackbar
import com.example.myapplication.R

class AddNewFragment : BaseFragment<FragmentAddNewBinding>(FragmentAddNewBinding::inflate) {

    private val viewModel: CardViewModel by activityViewModels()

    override fun listeners() {
        btnAdd()
        btnBack()
    }

    override fun bind() {
        binding.radioMaster.isChecked = true
    }

    override fun observers() {}

    private fun btnAdd() {
        binding.addCardButton.setOnClickListener {
            val name = binding.cardholderInput.text.toString().trim()
            var number = binding.etCardNumberInputId.text.toString().trim()
            val valid = binding.etExpiryInputId.text.toString().trim()
            val cvv = binding.cvvInput.text.toString().trim()

            if (!validateInputs(name, number, valid, cvv)) return@setOnClickListener

            number = number.chunked(4).joinToString("     ")

            val selectedType = when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioVisa.id -> Type.VISA
                else -> Type.MASTERCARD
            }

            val newCard = Card(
                id = (viewModel.cards.value.maxOfOrNull { it.id } ?: 0) + 1,
                cardNumber = number,
                name = name,
                valid = valid,
                cvv = cvv.toInt(),
                type = selectedType
            )

            viewModel.addCard(newCard)

            Snackbar.make(binding.root, "Card added successfully", Snackbar.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun validateInputs(name: String, number: String, valid: String, cvv: String): Boolean {
        var hasError = false

        if (!isValidCardNumber(number)) {
            binding.etCardNumberInputId.error = "Card number must be exactly 16 digits"
            hasError = true
        } else binding.etCardNumberInputId.error = null

        if (!isValidName(name)) {
            binding.cardholderInput.error = "Enter first and last name (letters only)"
            hasError = true
        } else binding.cardholderInput.error = null

        if (!isValidValidThru(valid)) {
            binding.etExpiryInputId.error = "Valid Thru must be in MM/YY format"
            hasError = true
        } else binding.etExpiryInputId.error = null

        if (!isValidCvv(cvv)) {
            binding.cvvInput.error = "CVV must be exactly 3 digits"
            hasError = true
        } else binding.cvvInput.error = null

        return !hasError
    }

    private fun isValidCvv(cvv: String): Boolean {
        return cvv.length == 3 && cvv.all { it.isDigit() }
    }

    private fun isValidCardNumber(number: String): Boolean {
        return number.length == 16 && number.all { it.isDigit() }
    }

    private fun isValidName(name: String): Boolean {
        val parts = name.split(" ")
        if (parts.size < 2) return false
        return parts.all { it.all { ch -> ch.isLetter() } }
    }

    private fun isValidValidThru(valid: String): Boolean {
        if (valid.length != 5 || valid[2] != '/') return false
        val month = valid.substring(0, 2).toIntOrNull() ?: return false
        val year = valid.substring(3, 5).toIntOrNull() ?: return false
        return month in 1..12 && year in 0..99
    }

    private fun btnBack() {
        binding.ibBackId.setOnClickListener {
            findNavController().navigate(R.id.action_addNewFragment_to_paymentFragment)
        }
    }
}


