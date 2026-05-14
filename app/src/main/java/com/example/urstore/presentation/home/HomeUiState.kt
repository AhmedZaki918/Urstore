package com.example.urstore.presentation.home

import com.example.urstore.data.model.categories.CategoriesDto
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.data.model.offer.OfferDto
import com.example.urstore.util.RequestState

data class HomeUiState(
    val homeState: RequestState = RequestState.IDLE,
    val categoriesState: RequestState = RequestState.IDLE,
    val homeCategories: List<CategoriesDto>? = emptyList(),
    val popularResponse: List<DrinksDataDto>? = emptyList(),
    val offersResponse : List<OfferDto>? = emptyList(),
    var firstName: String = "",
    var lastName: String = "",
    var isUserLoggedIn: Boolean = false,
    var isLoginDialogActive: Boolean = false
)
