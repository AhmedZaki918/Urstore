package com.example.urstore.data.model.auth_dto.login

data class LoginDto(
    val data: LoginData? = LoginData(),
    val message: String = "",
    val status: Int = 0
)