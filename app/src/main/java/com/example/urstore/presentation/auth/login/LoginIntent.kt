package com.example.urstore.presentation.auth.login

import com.example.urstore.util.AuthField

sealed class LoginIntent {
    object Login : LoginIntent()
    object ForgotPassword : LoginIntent()
    object SignUp : LoginIntent()
    object Skip : LoginIntent()
    data class UpdateTextField(
        var textFieldType: AuthField,
        var value: String
    ) : LoginIntent()
}