package com.example.urstore.util

sealed class UiEffect {
    data class ShowSnackbar(
        val message: String,
        val actionLabel: String
    ) : UiEffect()

    data class NavigateWithTwoArgs<T, E>(
        val firstArg: T,
        val secondArg: E,
    ) : UiEffect()

    data class NavigateWithOneArg<T>(
        val arg: T
    ) : UiEffect()

    object Navigate : UiEffect()
}