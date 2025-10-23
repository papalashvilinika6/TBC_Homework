package com.example.myapplication.screen.home

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.users.UsersAdapter
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun bind() {
        setupObservers()
        setupRecyclerView()
    }

    override fun listeners() {
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
            val randomIndex = userViewModel.getRandomUserIndex()
            if (randomIndex != -1) {
                findNavController().navigate(R.id.action_mainFragmentId_to_updateFragmentId)
            } else {
                binding.twStatusId.text = getString(R.string.is_empty)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvUsersId.layoutManager = LinearLayoutManager(requireContext())

        userViewModel.users.observe(viewLifecycleOwner) { userList ->
            val adapter = UsersAdapter(userList) { clickedUser ->
                val action = HomeFragmentDirections.actionMainFragmentIdToUpdateFragmentId(
                    userList.indexOf(clickedUser)
                )
                findNavController().navigate(action)
            }
            binding.rvUsersId.adapter = adapter
        }
    }


    private fun setupObservers() {
        userViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            updateUserCounts()
        })

        userViewModel.deletedCount.observe(viewLifecycleOwner, Observer { deleted ->
            updateUserCounts()
        })

        userViewModel.addResult.observe(viewLifecycleOwner, Observer { result ->
            result?.let { success ->
                displayAddResult(success)
                userViewModel.clearAddResult()
            }
        })

        userViewModel.updateResult.observe(viewLifecycleOwner, Observer { result ->
            result?.let { success ->
                displayUpdateResult(success)
                userViewModel.clearUpdateResult()
            }
        })

        userViewModel.removeResult.observe(viewLifecycleOwner, Observer { result ->
            result?.let { success ->
                displayRemoveResult(success)
                userViewModel.clearRemoveResult()
            }
        })
    }

    private fun updateUserCounts() {
        binding.twActiveUsersId.text = getString(R.string.active, userViewModel.getActiveUsersCount())
        binding.twDeletedUsersId.text = getString(R.string.deleted, userViewModel.getDeletedUsersCount())
    }

    private fun displayAddResult(success: Boolean) {
        with(binding.twStatusId) {
            if (success) {
                setText(R.string.success)
                setTextColor(Color.GREEN)
            } else {
                setText(R.string.fail)
                setTextColor(Color.RED)
            }
        }
    }

    private fun displayUpdateResult(success: Boolean) {
        with(binding.twStatusId) {
            if (success) {
                setText(R.string.updated_email)
                setTextColor(Color.GREEN)
            } else {
                setText(R.string.fail)
                setTextColor(Color.RED)
            }
        }
    }

    private fun displayRemoveResult(success: Boolean) {
        with(binding.twStatusId) {
            if (success) {
                setText(R.string.successDelete)
                setTextColor(Color.GREEN)
            } else {
                setText(R.string.fail)
                setTextColor(Color.RED)
            }
        }
    }

}