package com.example.appstory.user.signup

import androidx.lifecycle.ViewModel
import com.example.appstory.data.UserRepository

class SignUpViewModel(private val repository: UserRepository): ViewModel() {

    fun registerUser(name: String, email: String, password: String) = repository.signup(name,email,password)


}