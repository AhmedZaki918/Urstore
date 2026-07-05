package com.example.urstore.data.repository

import com.example.urstore.data.model.cart.add.AddCartRequest
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import com.example.urstore.util.generateToken
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
            token = generateToken(token),
            body = AddCartRequest(drinkId, count, userId)
        )
    }

    suspend fun initCartItems(token: String) = safeApiCall {
        api.cartItems(generateToken(token))
    }

    suspend fun initDeleteCart(token: String) = safeApiCall {
        api.deleteCart(generateToken(token))
    }

    suspend fun initRemoveFromCart(token: String, cartId: Int) = safeApiCall {
        api.removeFromCart(cartId, generateToken(token))
    }

    suspend fun initIncreaseQty(token: String, cartId: Int) = safeApiCall {
        api.increaseQuantity(cartId,generateToken(token))
    }

    suspend fun initDecreaseQty(token: String, cartId: Int) = safeApiCall {
        api.decreaseQuantity(cartId,generateToken(token))
    }
}