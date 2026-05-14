package com.example.urstore.presentation.see_all

import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.util.RequestState

data class SeeAllUiState(
    val seeAllState: RequestState = RequestState.IDLE,
    val seeAllResponse: List<DrinksDataDto>? = emptyList(),
    var isLoginDialogActive: Boolean = false,
    var isUserLoggedIn: Boolean = false
)