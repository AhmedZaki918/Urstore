package com.example.urstore.data.model.drinks_dto

data class DrinksDto(
    val count: Int? = 0,
    val data: List<DrinksDataDto> = listOf(),
    val pageIndex: Int? = 0,
    val pageSize: Int? = 0
)