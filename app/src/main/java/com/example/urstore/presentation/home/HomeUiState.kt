package com.example.urstore.presentation.home

import com.example.urstore.data.model.HomeCategory
import com.example.urstore.data.model.HomePopular
import com.example.urstore.util.RequestState


data class HomeUiState(
    val homeCategories: List<HomeCategory> = emptyList(),
    val homePopular: List<HomePopular> = emptyList(),
    val addedToCartState: RequestState = RequestState.IDLE
)
