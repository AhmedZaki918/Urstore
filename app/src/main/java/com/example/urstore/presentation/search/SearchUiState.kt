package com.example.urstore.presentation.search

import com.example.urstore.data.model.drinks.DrinksDataDto

data class SearchUiState(
    val searchResponse: List<DrinksDataDto>? = emptyList(),
    var isLoginDialogActive: Boolean = false,
    var isUserLoggedIn: Boolean = false,
    var searchKeyword : String = "",
    var isSearchInitialized : Boolean = false
)
