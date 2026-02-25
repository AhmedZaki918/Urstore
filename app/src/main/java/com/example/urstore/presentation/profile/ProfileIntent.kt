package com.example.urstore.presentation.profile

import com.example.urstore.presentation.home.HomeIntent

sealed class ProfileIntent {
    object Logout : ProfileIntent()
}