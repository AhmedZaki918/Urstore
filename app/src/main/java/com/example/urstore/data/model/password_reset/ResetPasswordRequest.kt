package com.example.urstore.data.model.password_reset

data class ResetPasswordRequest(
    var email: String = "",
    var Otp : String = "",
    var NewPassword : String = ""
)
