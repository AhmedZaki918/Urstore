package com.example.urstore.presentation.auth.login

import com.example.urstore.util.RequestState

data class LoginUiState(
    val loginState: RequestState = RequestState.IDLE,
    val responseMessage : String = "",
    var password : String = "",
    var email : String = ""
)
