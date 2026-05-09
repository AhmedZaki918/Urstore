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

    suspend fun initDeleteCart(token: String) = safeApiCall {
        api.deleteCart("Bearer $token")
    }

    suspend fun initRemoveFromCart(token: String, cartId: Int) = safeApiCall {
        api.removeFromCart(cartId, "Bearer $token")
    }

    suspend fun initIncreaseQty(token: String, cartId: Int) = safeApiCall {
        api.increaseQuantity(cartId,"Bearer $token")
    }

    suspend fun initDecreaseQty(token: String, cartId: Int) = safeApiCall {
        api.decreaseQuantity(cartId,"Bearer $token")
    }
}