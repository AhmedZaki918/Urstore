package com.example.urstore.presentation.auth.user_updare

import com.example.urstore.util.RequestState

data class EditProfileUiState(
    val profileState: RequestState = RequestState.IDLE,
    var fullName: String = "",
    var phoneNumber: String = "",
    var address: String = "",
    var email : String = ""
)
