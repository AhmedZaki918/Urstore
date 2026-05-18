package com.example.urstore.presentation.password_reset.forget_password

import com.example.urstore.util.AuthField

sealed class ForgetPasswordIntent {

    object SendCode : ForgetPasswordIntent()
    object GoBack : ForgetPasswordIntent()
    data class UpdateTextField(
        var textFieldType: AuthField,
        var value: String
    ) : ForgetPasswordIntent()
}