package com.example.urstore.presentation.see_all

import com.example.urstore.data.model.drinks_dto.DrinksDataDto

sealed class SeeAllIntent {
    data class AddToCart(
        val item: DrinksDataDto
    ) : SeeAllIntent()
}