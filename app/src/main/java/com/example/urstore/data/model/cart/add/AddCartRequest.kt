package com.example.urstore.data.model.cart.add

data class AddCartRequest(
    val DrinkId : Int = 0,
    val Count : Int = 0,
    val ApplicationUserId : String = ""
)