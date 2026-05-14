package com.example.urstore.data.model.auth.login

data class LoginDto(
    val address: String? = "",
    val clientId: String? = "",
    val displayName: String? = "",
    val email: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val phone: String? = "",
    val token: String? = ""
)