package com.example.buzzly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buzzly.data.User
import com.example.buzzly.data.UserRepository

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _users = MutableLiveData<List<User>>()
    private val _currentUser = MutableLiveData<User>()

    val currentUser: LiveData<User> = _currentUser
    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        userRepository.getAllUsers { userList ->
            _users.postValue(userList)
        }
    }

    fun loadCurrentUser(){
        userRepository.getCurrentUser { user->
            user?.let {_currentUser.postValue(it)
            }
        }
    }
}