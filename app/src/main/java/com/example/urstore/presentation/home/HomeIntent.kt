package com.example.urstore.presentation.home

import com.example.urstore.data.model.drinks.DrinksDataDto

sealed class HomeIntent {
    data class OnCategoryClicked(
        val id: Int
    ) : HomeIntent()

    data class AddToCart(
        val item: DrinksDataDto
    ) : HomeIntent()

    object RetryHome : HomeIntent()

    data class ShowDialog(var isActive: Boolean) : HomeIntent()
    data class GoToDetails(val id : Int) : HomeIntent()
    object Login : HomeIntent()
    object Search : HomeIntent()
}