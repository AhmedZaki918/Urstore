package com.example.urstore.data.model.auth_dto.register

data class RegisterDto(
    val data: Data = Data(),
    val message: String = "",
    val status: Int = 0
)