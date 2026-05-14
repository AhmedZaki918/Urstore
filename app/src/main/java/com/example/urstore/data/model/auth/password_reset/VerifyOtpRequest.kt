package com.example.urstore.data.model.auth.password_reset

data class VerifyOtpRequest(
    var email: String = "",
    var otp: String = ""
)
