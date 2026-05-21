package com.example.urstore.data.model.auth.update_user

data class UpdateUserRequest(
    val address: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = ""
)