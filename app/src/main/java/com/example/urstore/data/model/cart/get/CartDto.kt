package com.example.urstore.data.model.cart.get

data class CartDto(
    val shoppingCartList: List<ShoppingCart> = emptyList(),
    val totalAmount: Int? = 0
)