package com.example.urstore.presentation.see_all

import com.example.urstore.data.model.drinks.DrinksDataDto

sealed class SeeAllIntent {
    data class AddToCart(
        val item: DrinksDataDto
    ) : SeeAllIntent()

    data class ShowDialog(var isActive: Boolean) : SeeAllIntent()
    object Login : SeeAllIntent()
}