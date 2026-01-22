package com.example.urstore.data.model

data class Cart(
    val id: Int = 0,
    val title: String = "",
    val caption: String = "",
    val description: String = "",
    val image: Int,
    val unitPrice: Double =0.0,
    var totalPrice : Double = 0.0,
    val rate : Double = 0.0,
    var qty : Int = 0
)
