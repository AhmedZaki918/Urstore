package com.example.urstore.util

sealed class UiEffect {
    data class ShowSnackbar(
        val message: String,
        val actionLabel: String
    ) : UiEffect()

    data class Navigate(val route: String) : UiEffect()
    data class ClearBackStack(val route: String) : UiEffect()
    object PobBackStack : UiEffect()
}