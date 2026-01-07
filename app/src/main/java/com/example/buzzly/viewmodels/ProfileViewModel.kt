package com.example.buzzly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buzzly.data.User
import com.example.buzzly.data.UserRepository

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _users = MutableLiveData<List<User>>()

    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        userRepository.getAllUsers { userList ->
            _users.postValue(userList)
        }
    }
}