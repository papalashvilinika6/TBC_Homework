package com.example.myapplication.screen.update_user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.helper.Helper
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentUpdateBinding

class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate) {
    private var selectedUserIndex = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun setupObservers() {
        selectedUserIndex = UpdateFragmentArgs.fromBundle(requireArguments()).userIndex

        val user = userViewModel.getCurrentUsers().getOrNull(selectedUserIndex)
        user?.let {
            binding.etFirstNameId.setText(it.firstName)
            binding.etLastNameId.setText(it.lastName)
            binding.etAgeId.setText(it.age.toString())
            binding.etEmailId.setText(it.email)
        }

        userViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            if (selectedUserIndex in users.indices) {
                editTextImutable()
            }
        })
    }

    override fun listeners() {
        btnUpdate()
        btnRemove()
    }



    override fun bind() {
        setupObservers()
    }

    private fun btnRemove() {
        binding.btnRemoveUsersId.setOnClickListener {
            userViewModel.removeUser(selectedUserIndex)
            findNavController().navigate(R.id.action_updateFragmentId_to_mainFragmentId)
        }
    }

    private fun btnUpdate() = with(binding) {
        btnUpdateUsersId.setOnClickListener {
            val newFirstName = etFirstNameId.text.toString().trim()
            val newLastName = etLastNameId.text.toString().trim()
            val newAge = etAgeId.text.toString().toIntOrNull() ?: 0

            if (allInputsValid(
                    firstName = newFirstName,
                    lastName = newLastName,
                    age = newAge.toString()
                    )
                ) {
                    userViewModel.updateUser(selectedUserIndex, newFirstName, newLastName, newAge)
                    findNavController().navigate(R.id.action_updateFragmentId_to_mainFragmentId)
                }
            }

    }

    private fun editTextImutable() = with(binding){
        val users = userViewModel.getCurrentUsers()
        if (selectedUserIndex in users.indices) {
            etEmailId.apply {
                keyListener = null
                setText(users[selectedUserIndex].email)
                }

            etFirstNameId.setText(users[selectedUserIndex].firstName)
            etLastNameId.setText(users[selectedUserIndex].lastName)
            etAgeId.setText(users[selectedUserIndex].age.toString())
            }
    }


    private fun allInputsValid(firstName:String, lastName:String, age:String) : Boolean {
        return if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
            Helper.showSnackbar(binding.root, getString(R.string.fields))
            false
        } else true
    }

}