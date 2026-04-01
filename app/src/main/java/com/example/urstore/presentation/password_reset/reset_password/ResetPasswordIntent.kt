package com.example.urstore.presentation.password_reset.reset_password

import com.example.urstore.util.AuthField

sealed class ResetPasswordIntent {

    data class UpdateTextField(
        var textFieldType: AuthField,
        var value: String
    ) : ResetPasswordIntent()

    data object ResetPassword : ResetPasswordIntent()
}