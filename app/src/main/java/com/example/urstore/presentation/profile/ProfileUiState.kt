package com.example.urstore.presentation.profile

import com.example.urstore.util.RequestState

data class ProfileUiState(
    val profileState: RequestState = RequestState.IDLE,
    var isLoginDialogActive : Boolean = false,
    var authName : String = "",
    var authCaption: String = "",
    var isUserLoggedIn : Boolean = false
)
