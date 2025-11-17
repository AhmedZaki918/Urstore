package com.example.urstore.presentation.home

import com.example.urstore.data.model.HomeCategory
import com.example.urstore.data.model.HomePopular

data class HomeUiState(
    val homeCategories: List<HomeCategory> = emptyList(),
    val homePopular: List<HomePopular> = emptyList()
)
