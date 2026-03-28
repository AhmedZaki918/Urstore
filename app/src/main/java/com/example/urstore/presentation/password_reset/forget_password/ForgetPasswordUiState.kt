package com.example.urstore.presentation.password_reset.forget_password

import com.example.urstore.util.RequestState

data class ForgetPasswordUiState(
    val forgetPasswordState: RequestState = RequestState.IDLE,
    var email: String = "",
    var responseMessage: String? = ""
)
