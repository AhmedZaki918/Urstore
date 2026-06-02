package com.example.urstore.util

import com.example.urstore.data.model.cart.get.CartDto

sealed class CartIntentSharedVM {
    data class SaveCartItems(
        val cartItems: CartDto
    ) : CartIntentSharedVM()
}