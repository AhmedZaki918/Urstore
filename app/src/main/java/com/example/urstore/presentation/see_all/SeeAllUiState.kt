package com.example.urstore.presentation.see_all

import androidx.paging.PagingData
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow

data class SeeAllUiState(
    val seeAllState: RequestState = RequestState.IDLE,
    val seeAllResponse: List<DrinksDataDto>? = emptyList(),
    var isLoginDialogActive: Boolean = false,
    var isUserLoggedIn: Boolean = false
)