package com.example.urstore.presentation.home

import com.example.urstore.data.model.HomeCategory
import com.example.urstore.data.model.drinks_dto.DrinksDto
import com.example.urstore.util.RequestState


data class HomeUiState(
    val homeState: RequestState = RequestState.IDLE,
    val homeCategories: List<HomeCategory> = emptyList(),
    val popularResponse: DrinksDto = DrinksDto(),
    val addedToCartState: RequestState = RequestState.IDLE
)
