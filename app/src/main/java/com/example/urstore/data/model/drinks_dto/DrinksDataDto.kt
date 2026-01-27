package com.example.urstore.data.model.drinks_dto

data class DrinksDataDto(
    val category: String = "",
    val categoryId: Int = 0,
    val description: String = "",
    val id: Int = 0,
    val imageName: String = "",
    val isBest: Boolean = false,
    val price: Int = 0,
    val rate: Double = 0.0,
    val title: String = ""
)