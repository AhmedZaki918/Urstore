package com.example.urstore.presentation.details

sealed class DetailsIntent {
    data class OnSizeClicked(
        val id: Int
    ) : DetailsIntent()

    data class DisplayProductDetails(
        val id: Int
    ) : DetailsIntent()
}