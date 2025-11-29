package com.example.urstore.presentation.cart

import com.example.urstore.data.model.Cart

sealed class CartIntent {
    data class RemoveItem(val item: Cart) : CartIntent()
    data class IncreaseQuantity(val id: Int) : CartIntent()
    data class DecreaseQuantity(val id: Int) : CartIntent()
}