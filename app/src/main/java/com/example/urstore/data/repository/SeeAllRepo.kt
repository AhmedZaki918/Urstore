package com.example.urstore.data.repository

import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import jakarta.inject.Inject

class SeeAllRepo @Inject constructor(
    private val api: APIService
) : SafeApiCall {

    suspend fun getAllDrinks() = safeApiCall {
        api.allDrinks()
    }
}