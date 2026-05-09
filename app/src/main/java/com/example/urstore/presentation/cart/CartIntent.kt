package com.example.urstore.presentation.cart

sealed class CartIntent {
    data class RemoveItem(val cartId: Int) : CartIntent()
    data class IncreaseQuantity(val id: Int) : CartIntent()
    data class DecreaseQuantity(val id: Int) : CartIntent()
    data class ShowDialog(var isActive: Boolean) : CartIntent()
    object DeleteCart : CartIntent()
    object RetryFetchCart : CartIntent()
}