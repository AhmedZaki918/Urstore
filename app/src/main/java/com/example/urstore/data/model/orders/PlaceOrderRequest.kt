package com.example.urstore.data.model.orders

data class PlaceOrderRequest(
    val ApplicationUserId : String = "",
    val ClientName : String = "",
    val Clientphone : String = "",
    val email : String = "",
    val ClientAddress : String = ""
)
