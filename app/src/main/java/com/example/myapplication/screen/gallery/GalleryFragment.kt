package com.example.myapplication.screen.gallery

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.category.Category
import com.example.myapplication.category.CategoryAdapter
import com.example.myapplication.common.BaseFragment
import com.example.myapplication.databinding.FragmentGalleryBinding
import com.example.myapplication.product.ProductAdapter
import com.example.myapplication.R

class GalleryFragment : BaseFragment<FragmentGalleryBinding>(FragmentGalleryBinding::inflate) {

    private val viewModel: GalleryViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun bind() {
        setupCategories()
        setupProducts()
        observeViewModel()
    }

    private fun setupCategories() {
        val adapter = CategoryAdapter { selected ->
            GalleryViewModel
        }
        binding.rvCategories.adapter = adapter
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        GalleryViewModel.categories.observe(viewLifecycleOwner) { categoryList ->
            adapter.submitList(categoryList)
        }
    }

    private fun setupProducts() {
        val adapter = ProductAdapter()
        binding.rvItems.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItems.adapter = adapter

        GalleryViewModel.products.observe(viewLifecycleOwner) { productList ->
            adapter.submitList(productList)
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { list ->
            productAdapter.submitList(list)
        }
    }

}