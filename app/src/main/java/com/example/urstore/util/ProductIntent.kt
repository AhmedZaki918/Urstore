package com.example.urstore.util

import com.example.urstore.data.model.drinks.DrinksDataDto

sealed class ProductIntent {
    data class OnProductClicked(
        val item: DrinksDataDto
    ) : ProductIntent()
}