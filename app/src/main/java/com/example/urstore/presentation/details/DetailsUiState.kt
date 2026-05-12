package com.example.urstore.presentation.details

import com.example.urstore.data.model.ProductSize
import com.example.urstore.util.RequestState

data class DetailsUiState(
    val productSize: List<ProductSize> = emptyList(),
    val addToCartState: RequestState = RequestState.IDLE,
    val quantity : Int = 1,
    val clientId : String = "",
    val token : String = "",
    var isLoginDialogActive: Boolean = false,
    var isUserLoggedIn: Boolean = false
)
