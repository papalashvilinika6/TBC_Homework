package com.example.myapplication.presentation.ui.user

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentUserBinding
import com.example.myapplication.presentation.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment :
    BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()

    override fun listeners() {

        binding.buttonSave.setOnClickListener {
            val first = binding.editFirstName.text.toString()
            val last = binding.editLastName.text.toString()
            val email = binding.editEmail.text.toString()

            viewModel.onEvent(UserEvent.Save(first, last, email))
        }

        binding.buttonRead.setOnClickListener {
            viewModel.onEvent(UserEvent.Read)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                binding.progressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                state.loadedUser?.let { user ->
                    binding.textResult.text = """
                        ${user.firstName} ${user.lastName}
                        ${user.email}
                    """.trimIndent()
                }
            }
        }
    }
}

