package com.example.urstore.presentation.cart

import com.example.urstore.data.model.Cart
import com.example.urstore.data.model.cart.ItemQuantity
import com.example.urstore.data.model.cart.get.CartDto
import com.example.urstore.util.RequestState

data class CartUiState(
    val cartResponse: CartDto? = CartDto(),
    val cartState : RequestState = RequestState.IDLE,
    var isCartDialogActive : Boolean = false
)