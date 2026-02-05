package com.example.urstore.presentation.see_all

import com.example.urstore.data.model.drinks_dto.DrinksDto
import com.example.urstore.util.RequestState

data class SeeAllUiState(
    val seeAllState: RequestState = RequestState.IDLE,
    val seeAllResponse: DrinksDto = DrinksDto()
)