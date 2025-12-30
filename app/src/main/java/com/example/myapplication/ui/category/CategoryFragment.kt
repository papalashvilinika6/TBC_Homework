package com.example.myapplication.ui.category

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCategoryBinding
import com.example.myapplication.ui.common.BaseFragment
import com.example.myapplication.ui.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private var loadingAnimation: ObjectAnimator? = null

    override fun bind() {
        setupRecyclerView()
        viewModel.onEvent(CategoryEvent.Load)
    }

    override fun listeners() {
        setupSearchInput()
    }

    override fun observers() {
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
        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(
                CategoryEvent.SearchQueryChanged(text?.toString().orEmpty())
            )
        }
    }


    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: CategoryState) = with(binding) {
        loadingImageView.setVisible(state.loading)
        if (state.loading) {
            startLoadingAnimation()
        } else {
            stopLoadingAnimation()
        }
        recyclerViewCategories.setVisible(!state.loading && state.error == null)

        textViewError.setVisible(state.error != null)
        textViewError.text = state.error

        adapter.submitList(state.categories)
    }


    private fun startLoadingAnimation() {
        if (loadingAnimation == null) {
            loadingAnimation = ObjectAnimator.ofFloat(
                binding.loadingImageView,
                View.ROTATION,
                0f,
                360f
            ).apply {
                duration = 1000
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
            }
        }
        loadingAnimation?.start()
    }

    private fun stopLoadingAnimation() {
        loadingAnimation?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopLoadingAnimation()
        loadingAnimation = null
    }
}

