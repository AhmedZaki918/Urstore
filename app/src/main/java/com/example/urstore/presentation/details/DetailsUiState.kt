package com.example.urstore.presentation.details

import com.example.urstore.R
import com.example.urstore.data.model.HomePopular
import com.example.urstore.data.model.ProductSize
import com.example.urstore.util.RequestState

data class DetailsUiState(
    val productSize: List<ProductSize> = emptyList(),
    val popularItem : HomePopular = HomePopular(image = R.drawable.drink_1),
    val addedToCartState: RequestState = RequestState.IDLE
)
