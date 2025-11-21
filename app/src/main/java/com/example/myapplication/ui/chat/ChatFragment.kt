package com.example.myapplication.ui.chat

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter

    private val searchWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.etSearch.isEnabled) {
                viewModel.onEvent(ChatEvent.OnSearchQueryChanged(s?.toString().orEmpty()))
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    override fun listeners() {
        btnSearch()
    }

    override fun bind() {
        setupRecycler()
        collectState()
        searchVisibility()
    }

    override fun observers() {
        viewModel.onEvent(ChatEvent.Load)
    }

    private fun btnSearch() {
        binding.ibSearch.setOnClickListener {
            viewModel.onEvent(ChatEvent.OnSearchButtonClicked)
        }
    }

    private fun searchVisibility() {
        binding.etSearch.isEnabled = false
        binding.etSearch.alpha = 0.5f
        binding.etSearch.addTextChangedListener(searchWatcher)
    }

    private fun setupRecycler() {
        adapter = ChatAdapter()
        binding.rvChats.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChats.adapter = adapter

    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->

                    adapter.submitList(state.filteredConversations)

                    binding.etSearch.isEnabled = state.isSearchEnabled
                    binding.etSearch.alpha = if (state.isSearchEnabled) 1f else 0.5f

                    if (!state.isSearchEnabled) {
                        binding.etSearch.setText("")
                    }
                }
            }
        }
    }

}