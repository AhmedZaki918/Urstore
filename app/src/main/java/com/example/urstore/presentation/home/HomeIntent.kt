package com.example.urstore.presentation.home

import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.presentation.auth.login.LoginIntent

sealed class HomeIntent {
    data class OnCategoryClicked(
        val id: Int
    ) : HomeIntent()

    data class AddToCart(
        val item: DrinksDataDto
    ) : HomeIntent()

    object RetryHome : HomeIntent()

    data class ShowDialog(var isActive: Boolean) : HomeIntent()

    object Login : HomeIntent()
}