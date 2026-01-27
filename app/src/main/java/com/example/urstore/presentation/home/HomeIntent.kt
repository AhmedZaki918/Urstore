package com.example.urstore.presentation.home

import com.example.urstore.data.model.drinks_dto.DrinksDataDto

sealed class HomeIntent {
    data class OnCategoryClicked(
        val id: Int
    ) : HomeIntent()

    data class AddToCart(
        val item: DrinksDataDto
    ) : HomeIntent()

    object RevertAddedToCartStateToIdle : HomeIntent()
}