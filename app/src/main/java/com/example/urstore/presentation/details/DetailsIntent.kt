package com.example.urstore.presentation.details

import com.example.urstore.data.model.drinks.ItemDetails

sealed class DetailsIntent {
    data class OnSizeClicked(
        val id: Int
    ) : DetailsIntent()

    data class AddToCart(
        val drinkId: Int
    )  : DetailsIntent()

    data class UpdateQuantity(
        val operation : String
    ) : DetailsIntent()

    data class ShowDialog(var isActive: Boolean) : DetailsIntent()

    data class AddToWishlist(val coffee: ItemDetails) : DetailsIntent()

    object Login : DetailsIntent()
    object GoBack : DetailsIntent()
}