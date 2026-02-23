package com.example.urstore.presentation.auth.signup

import com.example.urstore.util.SignupField

sealed class SignupIntent {
    object Signup : SignupIntent()

    data class UpdateTextField(
        var textFieldType: SignupField,
        var value: String
    ) : SignupIntent()

     object ClearErrorState : SignupIntent()
}