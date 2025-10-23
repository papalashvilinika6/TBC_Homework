package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.UsersList.users
import kotlin.random.Random

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var deleted = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayStatus()
        count()
        listeners()
    }

    private fun listeners() {
        btnAdd()
        btnUpdate()
    }

    private fun btnAdd() {
        binding.btnAddUsersId.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragmentId_to_addFragmentId)
        }
    }

    private fun btnUpdate(){
        binding.btnUpdateUsersId.setOnClickListener {
            val users = UsersList.users
            if (users.isNotEmpty()) {
                val randomIndex = Random.nextInt(users.size)
                val bundle = Bundle().apply{
                    putInt("random", randomIndex)
                    putInt("deleted", deleted)
                    }
                findNavController().navigate(R.id.action_mainFragmentId_to_updateFragmentId, bundle)
            } else {
                binding.twStatusId.text = getString(R.string.is_empty)
            }
        }
    }

    private fun displayStatus() {
        val checkAdd: Boolean? = arguments?.takeIf { it.containsKey("check") }?.getBoolean("check")
        val checkRemove: Boolean? = arguments?.takeIf { it.containsKey("removeCheck") }?.getBoolean("removeCheck")
        val checkUpdate: Boolean? = arguments?.takeIf { it.containsKey("updateCheck") }?.getBoolean("updateCheck")

        with(binding) {
            when {
                checkAdd == true -> {
                    twStatusId.setText(R.string.success)
                    twStatusId.setTextColor(Color.GREEN)
                }

                checkAdd == false -> {
                    twStatusId.setText(R.string.fail)
                    twStatusId.setTextColor(Color.RED)
                }

                checkRemove == true -> {
                    twStatusId.setText(R.string.successDelete)
                    twStatusId.setTextColor(Color.GREEN)
                }

                checkUpdate == true -> {
                    twStatusId.setText(R.string.updated_email)
                    twStatusId.setTextColor(Color.GREEN)
                }

                else -> {
                    twStatusId.setText(R.string.status)
                    twStatusId.setTextColor(Color.BLACK)
                }
            }
        }
    }

    private fun count() {
        val newDelete = arguments?.getInt("deleted", 0) ?: 0
        deleted = newDelete
        binding.twActiveUsersId.text = getString(R.string.active, users.size)
        binding.twDeletedUsersId.text = getString(R.string.deleted, newDelete)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}