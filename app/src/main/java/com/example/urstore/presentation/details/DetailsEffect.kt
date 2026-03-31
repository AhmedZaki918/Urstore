package com.example.urstore.presentation.details

sealed class DetailsEffect {
    data class ShowSnackbar(val message: String) : DetailsEffect()
}