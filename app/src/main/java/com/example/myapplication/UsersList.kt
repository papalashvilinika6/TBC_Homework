package com.example.myapplication

object UsersList {
    private val _users = mutableListOf<User>()
    val users: List<User> get() = _users

    fun addUser(user: User) {
        _users.add(user)
    }

    fun removeAt(index: Int) {
        if (index in 0 until _users.size) {
            _users.removeAt(index)
        }
    }

    fun updateUserAt(index: Int, newFirstName: String, newLastName: String, newAge: Int?) {
        if (index in _users.indices) {
            _users[index] = _users[index].copy(
                firstName = newFirstName,
                lastName = newLastName,
                age = newAge
            )
        }
    }

}