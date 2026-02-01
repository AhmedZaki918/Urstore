package com.example.urstore.data.model

data class ProductSize(
    val isPressed: Boolean = false,
    val id: Int = 0,
    val size: String = ""
)

data class ItemDetails(
    val description: String = "",
    val id: Int = 0,
    val imageName: String = "",
    val price: Int = 0,
    val rate: Double = 0.0,
    val title: String = ""
)