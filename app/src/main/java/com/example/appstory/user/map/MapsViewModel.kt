package com.example.appstory.user.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.appstory.data.UserRepository
import com.example.appstory.data.reference.UserModel
import com.example.appstory.data.response.StoryResponse

class MapsViewModel(private val repository: UserRepository): ViewModel() {
    val listStoryLocation: LiveData<StoryResponse> = repository.listStoryLocation

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStoryLocation(token:String) {
        repository.getStoryLocation(token)
    }
}