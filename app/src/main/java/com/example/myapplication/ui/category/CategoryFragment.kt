package com.example.myapplication.ui.category

import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCategoryBinding
import com.example.myapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter

    override fun bind() {
        setupRecyclerView()
        viewModel.onEvent(CategoryEvent.Load)
    }

    override fun listeners() {
        super.listeners()
        setupSearchInput()
    }

    override fun observers() {
        super.observers()
        observeState()
    }

    private fun setupRecyclerView() {
        adapter = CategoryAdapter()
        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CategoryFragment.adapter
        }
    }

    private fun setupSearchInput() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString().orEmpty()
                viewModel.onEvent(CategoryEvent.SearchQueryChanged(query))
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.isVisible = state.loading
                    binding.recyclerViewCategories.isVisible = !state.loading && state.error == null
                    binding.textViewError.isVisible = state.error != null
                    
                    state.error?.let { error ->
                        binding.textViewError.text = error
                    }
                    
                    adapter.submitList(state.categories)
                }
            }
        }
    }
}

