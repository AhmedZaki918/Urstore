package com.example.urstore.presentation.auth.login

import com.example.urstore.util.AuthField

sealed class LoginIntent {
    object Login : LoginIntent()

    data class UpdateTextField(
        var textFieldType: AuthField,
        var value: String
    ) : LoginIntent()

    object ClearErrorState : LoginIntent()
}