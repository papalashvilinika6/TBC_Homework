package com.example.myapplication.screen.add_user

import androidx.navigation.fragment.findNavController
import com.example.myapplication.helper.Helper
import com.example.myapplication.R
import com.example.myapplication.users.User
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {

    override fun listeners() {
        btnAdd()
    }

    override fun bind() {

    }

    private fun btnAdd() = with(binding) {
        btnAddUsersId.setOnClickListener {
            val firstName = etFirstNameId.text.toString().trim()
            val lastName = etLastNameId.text.toString().trim()
            val age = etAgeId.text.toString().toIntOrNull() ?: 0
            val email = etEmailId.text.toString().trim()

            if (!allInputsValid(firstName, lastName, age, email)) return@setOnClickListener

            if (!Helper.emailIsValid(email = email)) {
                Snackbar.make(root, getString(R.string.enter_real_email), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(firstName, lastName, age, email)
            userViewModel.addUser(user)

            findNavController().navigate(R.id.action_addFragmentId_to_mainFragmentId)
        }
    }

    private fun allInputsValid(firstName:String, lastName:String, age:Int, email:String) : Boolean {
        return if (firstName.isEmpty() || lastName.isEmpty() || age == 0 || email.isEmpty()) {
            Helper.showSnackbar(binding.root, getString(R.string.fields))
            false
        } else true
    }

}