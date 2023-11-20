package com.example.appstory.user.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstory.data.UserRepository
import com.example.appstory.data.reference.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {


    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun loginUser(email: String, password:String) = repository.login(email, password)
}