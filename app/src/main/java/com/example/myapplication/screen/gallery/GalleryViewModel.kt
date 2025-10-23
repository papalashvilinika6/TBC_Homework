package com.example.myapplication.screen.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.category.Category
import com.example.myapplication.product.Product
import kotlin.text.get

class GalleryViewModel : ViewModel(){

    private val _selectedCategory = MutableLiveData<String>("All")
    val selectedCategory: LiveData<String> get() = _selectedCategory

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val productsMap = mapOf(
        "Dresses" to listOf(
            Product("Dress1", 100, 0),
            Product("Dress2", 100, 0),
            Product("Dress3", 100, 0),
            Product("Dress4", 100, 0)
        ),
        "Shirts" to listOf(
            Product("Shirt1", 100, 0),
            Product("Shirt2", 100, 0),
            Product("Shirt3", 100, 0),
            Product("Shirt4", 100, 0)
        ),
        "Trousers" to listOf(
            Product("Trouser1", 100, 0),
            Product("Trouser2", 100, 0),
            Product("Trouser3", 100, 0),
            Product("Trouser4", 100, 0)
        ),
        "Jeans" to listOf(
            Product("Jeans1", 100, 0),
            Product("Jeans2", 100, 0),
            Product("Jeans3", 100, 0),
            Product("Jeans4", 100, 0)
        ),
        "Jackets" to listOf(
            Product("Jacket1", 100, 0),
            Product("Jacket2", 100, 0),
            Product("Jacket3", 100, 0),
            Product("Jacket4", 100, 0)
        )
    )

    private val allProducts: Map<String, List<Product>> = productsMap +
            ("All" to productsMap.values.flatten())

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _products.value = allProducts[category] ?: emptyList()
    }

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        _categories.value = listOf(
            Category("All", 0),
            Category("Dresses", 0),
            Category("Shirts", 0),
            Category("Trousers", 0),
            Category("Jeans", 0),
            Category("Jackets", 0)
        )
        _products.value = allProducts["All"] 
    }
}