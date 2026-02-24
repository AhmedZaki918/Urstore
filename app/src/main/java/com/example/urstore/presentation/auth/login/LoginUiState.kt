package com.example.urstore.presentation.auth.login

import com.example.urstore.data.model.auth_dto.login.LoginData
import com.example.urstore.util.RequestState

data class LoginUiState(
    val loginState: RequestState = RequestState.IDLE,
    val loginResponse: LoginData? = LoginData(),
    val responseMessage : String = "",
    var password : String = "",
    var email : String = ""
)
