package com.example.urstore.presentation.wishlist

import com.example.urstore.data.local.CoffeeEntity

sealed class WishlistIntent {
    data class RemoveItem(val item: CoffeeEntity) : WishlistIntent()
}