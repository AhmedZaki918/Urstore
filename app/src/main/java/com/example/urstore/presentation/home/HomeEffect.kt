package com.example.urstore.presentation.home

import com.example.urstore.util.SnackbarState

sealed class HomeEffect {
    data class ShowSnackbar(
        val message: String,
        val requestState: String
    ) : HomeEffect()
}