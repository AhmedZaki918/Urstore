package com.example.urstore.presentation.auth.signup

import com.example.urstore.util.AuthField

sealed class SignupIntent {
    object Signup : SignupIntent()

    data class UpdateTextField(
        var textFieldType: AuthField,
        var value: String
    ) : SignupIntent()

     object ClearErrorState : SignupIntent()
}