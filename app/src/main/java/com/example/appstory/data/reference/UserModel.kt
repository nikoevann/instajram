package com.example.appstory.data.reference

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
