package com.example.urstore.presentation.wishlist

import com.example.urstore.data.local.CoffeeEntity

sealed class WishlistIntent {
    data class RemoveItem(val item: CoffeeEntity) : WishlistIntent()
    object DeleteAll : WishlistIntent()

    data class AddToCart(
        val id: Int
    ) : WishlistIntent()

    data class ShowDialog(var isActive: Boolean) : WishlistIntent()
}