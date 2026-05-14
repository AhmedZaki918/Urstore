package com.example.urstore.data.model.auth.password_reset

data class ResetPasswordRequest(
    var email: String = "",
    var Otp : String = "",
    var NewPassword : String = ""
)
