package com.example.urstore.presentation.profile

sealed class ProfileIntent {
    object Logout : ProfileIntent()
    data class ShowDialog(var isActive: Boolean) : ProfileIntent()
}