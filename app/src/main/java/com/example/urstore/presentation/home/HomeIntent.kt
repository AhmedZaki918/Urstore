package com.example.urstore.presentation.home

sealed class HomeIntent {
    data class OnCategoryClicked(
        val id: Int
    ) : HomeIntent()
}