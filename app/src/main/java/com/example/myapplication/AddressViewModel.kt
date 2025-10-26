package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressViewModel : ViewModel() {
    private val _addresses = MutableLiveData(
        listOf(
            Address(1,"My Home", "Tbilisi", R.drawable.home_logo),
            Address(2,"My Office", "Batumi", R.drawable.office_logo),
            Address(3,"My Office", "Kutaisi", R.drawable.office_logo)
        )
    )
    val addresses: LiveData<List<Address>> = _addresses

    private val _selectedAddress = MutableLiveData<Address?>()
    val selectedAddress: LiveData<Address?> = _selectedAddress

    fun selectAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun updateAddress(updated: Address) {
        _addresses.value = _addresses.value?.map {
            if (it.id == updated.id) updated else it
        }
    }


    fun addAddress(title: String, address: String) {
        val id = (_addresses.value?.size ?: 0) + 1

        val imageRes = when (title.trim().lowercase()) {
            "my home" -> R.drawable.home_logo
            "my office" -> R.drawable.office_logo
            else -> R.drawable.home_logo
        }

        val newAddress = Address(id, title, address, imageRes)
        _addresses.value = _addresses.value?.plus(newAddress)
    }
}

