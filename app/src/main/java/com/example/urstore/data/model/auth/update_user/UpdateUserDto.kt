package com.example.urstore.data.model.auth.update_user

data class UpdateUserDto(
    val displayName: String? = "",
    val address: String? = "",
    val phoneNumber: String? = "",
    val email: String? = ""
)
