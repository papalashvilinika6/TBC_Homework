package com.example.myapplication.gallery

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.category.CategoryAdapter
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentGalleryBinding
import com.example.myapplication.product.ProductAdapter


class GalleryFragment : BaseFragment<FragmentGalleryBinding>(FragmentGalleryBinding::inflate) {

    private val viewModel: GalleryViewModel by activityViewModels()

    private val categoryAdapter = CategoryAdapter { selected ->
        viewModel.selectCategory(selected)
    }
    private val productAdapter = ProductAdapter()

    override fun bind() {
        setupCategories()
        setupProducts()
        observeViewModel()
    }

    private fun setupCategories() {
        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupProducts() {
        binding.rvItems.adapter = productAdapter
        binding.rvItems.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.submitList(products)
        }
    }

}
