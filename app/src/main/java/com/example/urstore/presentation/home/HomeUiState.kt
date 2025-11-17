package com.example.urstore.presentation.home

import com.example.urstore.data.model.HomeCategory

data class HomeUiState(
    val homeCategories : List<HomeCategory> = emptyList(),
)


