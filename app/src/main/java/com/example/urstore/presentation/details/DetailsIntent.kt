package com.example.urstore.presentation.details

import com.example.urstore.data.model.HomePopular
import com.example.urstore.presentation.home.HomeIntent

sealed class DetailsIntent {
    data class OnSizeClicked(
        val id: Int
    ) : DetailsIntent()

    data class DisplayProductDetails(
        val id: Int
    ) : DetailsIntent()

    data class AddToCart(
        val item: HomePopular
    ) : DetailsIntent()

    object RevertAddedToCartStateToIdle : DetailsIntent()
}