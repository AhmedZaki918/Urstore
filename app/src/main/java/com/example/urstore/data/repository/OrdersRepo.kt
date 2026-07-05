package com.example.urstore.data.repository

import com.example.urstore.data.model.orders.PlaceOrderRequest
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import com.example.urstore.util.generateToken
import okhttp3.Address
import javax.inject.Inject

class OrdersRepo @Inject constructor(
    private val api: APIService
) : SafeApiCall {

    suspend fun placeOrder(
        token: String,
        userId: String,
        fullName: String,
        phoneNumber: String,
        email: String,
        address: String
    ) = safeApiCall {
        val request = PlaceOrderRequest(
            ApplicationUserId = userId,
            ClientName = fullName,
            email = email,
            Clientphone = phoneNumber,
            ClientAddress = address
        )

        api.placeOrder(
            body = request,
            token = generateToken(token)
        )
    }
}