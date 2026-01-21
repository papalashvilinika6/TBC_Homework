package com.example.myapp.data.remote.mapper

import com.example.myapp.data.remote.dto.ProductDto
import com.example.myapp.domain.model.Product

fun ProductDto.toDomain() = Product(
        id = id,
        title = title,
        price = price,
        image = image,
        category = category
    )
