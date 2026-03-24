package com.example.urstore.presentation.password_reset.enter_code

sealed class EnterCodeIntent {

    data class UpdateTextField(
        var index: Int,
        var value: String
    ) : EnterCodeIntent()

    object VerifyCode : EnterCodeIntent()
    object RevertStateToIdle : EnterCodeIntent()
}