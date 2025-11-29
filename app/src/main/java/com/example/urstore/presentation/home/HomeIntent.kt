package com.example.urstore.presentation.home

import com.example.urstore.data.model.HomePopular

sealed class HomeIntent {
    data class OnCategoryClicked(
        val id: Int
    ) : HomeIntent()

    data class AddToCart(
        val item: HomePopular
    ) : HomeIntent()

    object RevertAddedToCartStateToIdle : HomeIntent()
}