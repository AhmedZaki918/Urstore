package com.example.urstore.data.model.cart

data class ItemQuantity(
    val data: ItemQuantityData = ItemQuantityData()
)

data class ItemQuantityData(
    val totalAmount: Int = 0,
    val itemTotalPrice: Int = 0,
    val itemCount: Int = 0
)
