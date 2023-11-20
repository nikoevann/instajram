package com.example.appstory.user.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.appstory.data.UserRepository
import com.example.appstory.data.reference.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStory(token: String) = repository.getStory(token)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}