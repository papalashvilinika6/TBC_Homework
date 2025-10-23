package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Helper.showSnackbar
import com.example.myapplication.UsersList.users
import com.example.myapplication.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextImutable()
        btnUpdate()
        btnRemove()
    }

    private fun btnRemove() {
        binding.btnRemoveUsersId.setOnClickListener {
            val index = arguments?.getInt("index", 0) ?: 0
            var deleted = arguments?.getInt("deleted", 0) ?: 0

            UsersList.removeAt(index)
            deleted++

            val bundle = Bundle().apply {
                putBoolean("removeCheck", true)
                putInt("deleted", deleted)
            }

            findNavController().navigate(R.id.action_updateFragmentId_to_mainFragmentId, bundle)
        }
    }

    private fun btnUpdate() {
        with(binding) {
            btnUpdateUsersId.setOnClickListener {
                val index = arguments?.getInt("index", 0) ?: 0

                val newFirstName = etFirstNameId.text.toString().trim()
                val newLastName = etLastNameId.text.toString().trim()
                val newAge = etAgeId.text.toString().toIntOrNull()

                if (allInputsValid(
                        firstName = newFirstName,
                        lastName = newLastName,
                        age = newAge.toString()
                    )
                ) {
                    UsersList.updateUserAt(index, newFirstName, newLastName, newAge)
                    sendToMain()
                }
            }
        }
    }

    private fun editTextImutable() {
        with(binding) {
            val index = arguments?.getInt("index", 0) ?: 0

            etEmailId.apply {
                keyListener = null
                setText(users[index].email)
            }

            etFirstNameId.setText(users[index].firstName)
            etLastNameId.setText(users[index].lastName)
            etAgeId.setText(users[index].age.toString())
        }
    }

    private fun allInputsValid(firstName:String, lastName:String, age:String) : Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
            showSnackbar(binding.root , getString(R.string.fields))
            return false
        }
        return true
    }

    private fun sendToMain() {
        val bundle = Bundle().apply {
            putBoolean("updateCheck", true)
        }
        findNavController().navigate(R.id.action_updateFragmentId_to_mainFragmentId, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}