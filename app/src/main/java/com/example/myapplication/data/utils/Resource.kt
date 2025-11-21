package com.example.myapplication.data.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val message: String) : Resource<T>()
    data class Loader<out T>(val isLoading: Boolean) : Resource<T>()
}