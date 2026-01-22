package com.example.urstore.presentation.cart

import com.example.urstore.data.model.Cart
import com.example.urstore.util.RequestState

data class CartUiState(
    val cartItems: List<Cart> = emptyList(),
    val plusState: MutableMap<String, RequestState> = mutableMapOf(),
    val minusState: MutableMap<String, RequestState> = mutableMapOf(),
    val deleteState: MutableMap<String, RequestState> = mutableMapOf(),
    val subtotal : Double = 0.0
)