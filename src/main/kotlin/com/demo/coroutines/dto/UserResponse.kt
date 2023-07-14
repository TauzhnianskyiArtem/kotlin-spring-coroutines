package com.demo.coroutines.dto

data class UserResponse(
    val id: Long,
    val age: Int,
    val email: String,
    val name: String,
)