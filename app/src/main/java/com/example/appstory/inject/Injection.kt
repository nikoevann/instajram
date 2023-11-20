package com.example.appstory.inject

import android.content.Context
import com.example.appstory.data.UserRepository
import com.example.appstory.data.reference.UserPreference
import com.example.appstory.data.reference.dataStore
import com.example.appstory.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val users = runBlocking { preference.getSession().first() }
        val apiService = ApiConfig.getApiService(users.token)
        return UserRepository.getInstance(preference,apiService)
    }
}