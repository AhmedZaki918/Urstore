package com.example.urstore.presentation.wishlist

import com.example.urstore.data.local.CoffeeEntity

data class WishlistUiState(
    val drinks : List<CoffeeEntity> = emptyList(),
    var isDialogActive : Boolean = false
)
