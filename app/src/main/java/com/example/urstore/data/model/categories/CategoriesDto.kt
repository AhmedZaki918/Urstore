package com.example.urstore.data.model.categories

data class CategoriesDto(
    val id: Int = 0,
    val imageName: String = "",
    val mainCategory: String = "",
    val mainCategoryId: Int = 0,
    val title: String = "",
    var isClicked : Boolean = false
)