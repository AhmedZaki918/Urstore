package com.example.urstore.presentation.auth.signup

import com.example.urstore.util.RequestState

data class SignupUiState(
    val signupState: RequestState = RequestState.IDLE,
    var fullName : String = "",
    var email : String = "",
    var phoneNumber : String = "",
    var address: String = "",
    var password : String = "",
    var confirmPassword : String = ""
)

