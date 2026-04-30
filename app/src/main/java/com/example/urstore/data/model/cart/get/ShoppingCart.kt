package com.example.urstore.data.model.cart.get

data class ShoppingCart(
    val cartId: Int = 0,
    val count: Int = 0,
    val imageName: String = "",
    val itemId: Int = 0,
    val itemName: String = "",
    val itemPrice: Int = 0,
    val totalPrice: Int = 0
)