package com.example.urstore.presentation.details

sealed class DetailsIntent {
    data class OnSizeClicked(
        val id: Int
    ) : DetailsIntent()


//    data class AddToCartTest(
//        val item: DrinksDataDto
//    ) : DetailsIntent()

    data class AddToCart(
        val drinkId: Int
    )  : DetailsIntent()

    data class UpdateQuantity(
        val operation : String
    ) : DetailsIntent()
}