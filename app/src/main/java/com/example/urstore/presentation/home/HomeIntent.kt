package com.example.urstore.presentation.home

import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.presentation.cart.CartIntent

sealed class HomeIntent {
    data class OnCategoryClicked(
        val id: Int
    ) : HomeIntent()

    data class AddToCart(
        val item: DrinksDataDto
    ) : HomeIntent()

    object RetryHome : HomeIntent()
}