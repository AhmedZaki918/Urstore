package com.example.urstore.presentation.password_reset.reset_password

import com.example.urstore.util.RequestState

data class ResetPasswordUiState(
    val resetPasswordState: RequestState = RequestState.IDLE,
    var email: String = "",
    var otp: String = "",
    var password : String = "",
    var newPassword : String = "",
    var responseMessage : String? = ""
)
