package com.example.urstore.data.model.orders.details

data class OrderDetailsResponse(
    val clientAddress: String? = "",
    val clientName: String? = "",
    val clientphone: String? = "",
    val email: String? = "",
    val orderDate: String? = "",
    val orderNumber: Int? = 0,
    val orderStatus: String? = "",
    val orderTime: String? = "",
    val orderTotal: String? = "",
    val shoppingCartList: List<ShoppingCart> =emptyList()
)