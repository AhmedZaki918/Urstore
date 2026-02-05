package com.example.urstore.util

import com.example.urstore.data.model.drinks_dto.DrinksDataDto

sealed class ProductIntent {
    data class OnProductClicked(
        val item: DrinksDataDto
    ) : ProductIntent()
}