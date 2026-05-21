package com.example.urstore.presentation.profile

sealed class ProfileIntent {
    object Logout : ProfileIntent()
    object Login : ProfileIntent()
    object GoBack : ProfileIntent()
    object ChangePassword : ProfileIntent()
    object EditProfile : ProfileIntent()
    data class ShowDialog(var isActive: Boolean) : ProfileIntent()
}