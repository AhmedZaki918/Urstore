package com.example.urstore.presentation.cart

import com.example.urstore.data.model.Cart
import com.example.urstore.presentation.profile.ProfileIntent

sealed class CartIntent {
    data class RemoveItem(val item: Cart) : CartIntent()
    data class IncreaseQuantity(val id: Int) : CartIntent()
    data class DecreaseQuantity(val id: Int) : CartIntent()
    data class ShowDialog(var isActive: Boolean) : CartIntent()
    object DeleteCart : CartIntent()
}