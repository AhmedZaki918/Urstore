package com.example.urstore.data.model.auth_dto.register

data class Data(
    val address: String = "",
    val clientId: String = "",
    val displayName: String = "",
    val email: String? = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val token: String = ""
)