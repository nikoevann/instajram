package com.example.appstory.user.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.appstory.data.UserRepository
import com.example.appstory.data.reference.UserModel
import java.io.File

class PostViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun upload(token: String, uploadFile: File, description: String) = repository.upload(token, uploadFile, description)
}