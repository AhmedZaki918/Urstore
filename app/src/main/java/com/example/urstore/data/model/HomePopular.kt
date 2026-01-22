package com.example.urstore.data.model

data class HomePopular(
    val id: Int = 0,
    val title: String = "",
    val caption: String = "",
    val description: String = "",
    val image: Int,
    val price: Double =0.0,
    val rate : Double = 0.0
)
