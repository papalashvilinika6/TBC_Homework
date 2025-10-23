package com.example.myapplication.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<MutableList<User>>().apply {
        value = mutableListOf(
            User("Nika", "Papalashvili", 21, "papalashvilinika6@gmail.com"),
            User("Luka", "Papalashvili", 28, "papalashvililuka6@gmail.com"),
            User("Sandro", "Papalashvili", 18, "papalashvilisandro@gmail.com"),
        )
    }

    val users: LiveData<MutableList<User>> = _users

    private val _deletedCount = MutableLiveData<Int>().apply { value = 0 }
    val deletedCount: LiveData<Int> = _deletedCount

    private val _addResult = MutableLiveData<Boolean?>()
    val addResult: LiveData<Boolean?> = _addResult

    private val _updateResult = MutableLiveData<Boolean?>()
    val updateResult: LiveData<Boolean?> = _updateResult

    private val _removeResult = MutableLiveData<Boolean?>()
    val removeResult: LiveData<Boolean?> = _removeResult


    fun getCurrentUsers(): MutableList<User> = _users.value ?: mutableListOf()

    fun addUser(user: User): Boolean {
        val currentUsers = getCurrentUsers()
        return if (currentUsers.none { it.email == user.email }) {
            currentUsers.add(user)
            _users.value = currentUsers
            _addResult.value = true
            true
        } else {
            _addResult.value = false
            false
        }
    }

    fun updateUser(index: Int, firstName: String, lastName: String, age: Int): Boolean {
        val currentUsers = getCurrentUsers()
        return if (index in currentUsers.indices) {
            currentUsers[index] = currentUsers[index].copy(
                firstName = firstName,
                lastName = lastName,
                age = age
            )
            _users.value = currentUsers
            _updateResult.value = true
            true
        } else {
            _updateResult.value = false
            false
        }
    }

    fun removeUser(index: Int): Boolean {
        val currentUsers = getCurrentUsers()
        return if (index in currentUsers.indices) {
            currentUsers.removeAt(index)
            _users.value = currentUsers
            _deletedCount.value = (_deletedCount.value ?: 0) + 1
            _removeResult.value = true
            true
        } else {
            _removeResult.value = false
            false
        }
    }

    fun getRandomUserIndex(): Int {
        val currentUsers = getCurrentUsers()
        return if (currentUsers.isNotEmpty()) {
            Random.nextInt(currentUsers.size)
        } else -1
    }

    fun clearAddResult() {
        _addResult.value = null
    }

    fun clearUpdateResult() {
        _updateResult.value = null
    }

    fun clearRemoveResult() {
        _removeResult.value = null
    }

    fun getActiveUsersCount(): Int = getCurrentUsers().size

    fun getDeletedUsersCount(): Int = _deletedCount.value ?: 0
}