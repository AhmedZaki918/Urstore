package com.example.urstore.presentation.password_reset.enter_code

sealed class EnterCodeEffect {
    data class ShowSnackbar(
        val message: String,
        val requestState: String
    ) : EnterCodeEffect()

    data class Navigate(
        val email: String,
        val otp: String
    ) : EnterCodeEffect()
}