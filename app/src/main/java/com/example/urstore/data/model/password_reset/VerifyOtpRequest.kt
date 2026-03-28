package com.example.urstore.data.model.password_reset

data class VerifyOtpRequest(
    var email: String = "",
    var otp: String = ""
)
