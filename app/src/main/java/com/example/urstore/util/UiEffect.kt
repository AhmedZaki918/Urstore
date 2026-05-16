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

    object NavigateTest : UiEffect()

    data class Navigate(val route : String) :  UiEffect()

    data class ClearBackStack(val route : String) :  UiEffect()

    object PobBackStack : UiEffect()
}