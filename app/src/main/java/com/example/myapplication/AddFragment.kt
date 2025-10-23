package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Helper.emailIsValid
import com.example.myapplication.Helper.showSnackbar
import com.example.myapplication.databinding.FragmentAddBinding
import com.example.myapplication.UsersList.users
import com.google.android.material.snackbar.Snackbar


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        btnAdd()

    }

    private fun btnAdd() {
        with(binding) {

            btnAddUsersId.setOnClickListener {
                val firstName = etFirstNameId.text.toString().trim()
                val lastName = etLastNameId.text.toString().trim()
                val age = etAgeId.text.toString().trim()
                val email = etEmailId.text.toString().trim()

                if (!allInputsValid(firstName,lastName,age,email)) return@setOnClickListener

                if (!emailIsValid(email = email)) {
                    Snackbar.make(root, getString(R.string.enter_real_email), Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (users.none { it.email == email }) {
                    val user = User(firstName, lastName, age.toIntOrNull(), email)
                    UsersList.addUser(user)
                    sendToMain(true)
                } else {
                    sendToMain(false)
                }

            }
        }
    }

    private fun allInputsValid(firstName:String, lastName:String, age:String, email:String) : Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || email.isEmpty()) {
            showSnackbar(binding.root , getString(R.string.fields))
            return false
        }
        return true
    }

    private fun sendToMain(success: Boolean) {
        val bundle = Bundle().apply {
            putBoolean("check", success)
        }
        findNavController().navigate(R.id.action_addFragmentId_to_mainFragmentId, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}