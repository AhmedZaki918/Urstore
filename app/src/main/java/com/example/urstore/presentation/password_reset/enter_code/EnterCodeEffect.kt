package com.example.urstore.presentation.password_reset.enter_code

sealed class EnterCodeEffect {
    data class ShowToast(val message: String) : EnterCodeEffect()
    data class Navigate(
        val email: String,
        val otp: String
    ) : EnterCodeEffect()
}