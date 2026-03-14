package com.example.urstore.presentation.home

import com.example.urstore.data.model.HomeCategory
import com.example.urstore.data.model.ItemDetails
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.util.RequestState

data class HomeUiState(
    val homeState: RequestState = RequestState.IDLE,
    val homeCategories: List<HomeCategory> = emptyList(),
    val popularResponse: List<DrinksDataDto> = emptyList(),
    val addedToCartState: RequestState = RequestState.IDLE,
    var firstName : String = "",
    var lastName : String = ""
)
