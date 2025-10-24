package com.example.myapplication.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.category.Category
import com.example.myapplication.product.Product
import com.example.myapplication.R

typealias Drawable = R.drawable

class GalleryViewModel : ViewModel(){

    private val _selectedCategory = MutableLiveData<String>("All")
    val selectedCategory: LiveData<String> get() = _selectedCategory

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val productsMap = mapOf(
        "Dresses" to listOf(
            Product("Dress1", "$129", Drawable.dress1),
            Product("Dress2", "$99", Drawable.dress2),
            Product("Dress3", "$109", Drawable.dress3),
            Product("Dress4", "$169", Drawable.dress4)
        ),
        "Shirts" to listOf(
            Product("Shirt1", "$69", Drawable.shirt1),
            Product("Shirt2", "$59", Drawable.shirt2),
            Product("Shirt3", "$85", Drawable.shirt3),
            Product("Shirt4", "$99", Drawable.shirt4)
        ),
        "Trousers" to listOf(
            Product("Trouser1", "$119", Drawable.throusers1),
            Product("Trouser2", "$115", Drawable.throusers2),
            Product("Trouser3", "$129", Drawable.throusers3),
            Product("Trouser4", "$99", Drawable.throusers4)
        ),
        "Jeans" to listOf(
            Product("Jeans1", "$89", Drawable.jeans1),
            Product("Jeans2", "$100", Drawable.jeans2),
            Product("Jeans3", "$119", Drawable.jeans3),
            Product("Jeans4", "$99", Drawable.jeans4)
        ),
        "Jackets" to listOf(
            Product("Jacket1", "$79", Drawable.jacket1),
            Product("Jacket2", "$99", Drawable.jacket2),
            Product("Jacket3", "$129", Drawable.jacket3),
            Product("Jacket4", "$149", Drawable.jacket4)
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
            Category("All", Drawable.all),
            Category("Dresses", Drawable.dress_icon),
            Category("Shirts", Drawable.shirts_icon),
            Category("Trousers", Drawable.throusers_icon),
            Category("Jeans", Drawable.jeans_icon),
            Category("Jackets", Drawable.jackets_icon)
        )
        _products.value = allProducts["All"]
    }
}