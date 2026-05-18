package com.example.urstore.presentation.search

import com.example.urstore.data.model.drinks.DrinksDataDto

sealed class SearchIntent {
    data class Search(
        val query: String
    ): SearchIntent()

    object ClearSearch : SearchIntent()

    data class AddToCart(
        val item: DrinksDataDto
    ) : SearchIntent()

    data class ShowDialog(var isActive: Boolean) : SearchIntent()
    object Login : SearchIntent()
    object GoToDetails : SearchIntent()
    object GoBack : SearchIntent()
}