package com.example.urstore.data.repository

import com.example.urstore.data.model.cart.add.AddCartRequest
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import jakarta.inject.Inject

class CartRepo @Inject constructor(
    private val api: APIService
) : SafeApiCall {

    suspend fun initAddToCart(
        token: String,
        drinkId: Int,
        count: Int,
        userId: String
    ) = safeApiCall {
        api.addToCart(
            token = "Bearer $token",
            body = AddCartRequest(drinkId, count, userId)
        )
    }

    suspend fun initCartItems(token: String) = safeApiCall {
        api.cartItems("Bearer $token")
    }

    suspend fun initRemoveCart(token: String) = safeApiCall {
        api.removeCart("Bearer $token")
    }
}