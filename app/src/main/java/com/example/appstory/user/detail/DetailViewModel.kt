package com.example.appstory.user.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.appstory.data.UserRepository
import com.example.appstory.data.reference.UserModel

class DetailViewModel(private val repository: UserRepository): ViewModel() {
    val Loading = repository.Loading
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDetail(token: String, id: String) = repository.detailStory(token, id)
}