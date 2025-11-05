package com.example.myapplication.chat

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.chat_message.ChatAdapter
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentChatBinding
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate)  {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter

    override fun listeners() {
        btnSend()
    }

    override fun bind() {
        bindRecyclerView()
    }

    override fun observers() {
        observeMessages()
    }

    private fun btnSend() {
        binding.btnSendId.setOnClickListener {
            val text = binding.etMessageInputId.text.toString()
            viewModel.sendMessage(text)
            binding.etMessageInputId.text?.clear()
        }
    }

    private fun observeMessages() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }

    private fun bindRecyclerView() {
        adapter = ChatAdapter()
        binding.rvChatId.adapter = adapter
        binding.rvChatId.layoutManager =
            LinearLayoutManager(requireContext()).apply { reverseLayout = true }
    }


}