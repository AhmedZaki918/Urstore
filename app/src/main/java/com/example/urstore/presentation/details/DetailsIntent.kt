package com.example.urstore.presentation.details

import com.example.urstore.data.model.drinks_dto.DrinksDataDto

sealed class DetailsIntent {
    data class OnSizeClicked(
        val id: Int
    ) : DetailsIntent()

//    data class DisplayProductDetails(
//        val id: Int
//    ) : DetailsIntent()

    data class AddToCart(
        val item: DrinksDataDto
    ) : DetailsIntent()

    object RevertAddedToCartStateToIdle : DetailsIntent()
}