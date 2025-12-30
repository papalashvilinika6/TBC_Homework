package com.example.myapplication.data.remote.mapper

import com.example.myapplication.data.remote.dto.CategoryDto
import com.example.myapplication.domain.model.Category

fun CategoryDto.toDomain(depth: Int = 0, parentPath: List<String> = emptyList()): Category {
    val currentPath = parentPath + id
    return Category(
        id = id,
        name = name,
        nameDe = nameDe,
        createdAt = createdAt,
        bglNumber = bglNumber,
        bglVariant = bglVariant,
        orderId = orderId,
        main = main,
        children = children.map { it.toDomain(depth + 1, currentPath) },
        depth = depth,
        parentPath = parentPath
    )
}

