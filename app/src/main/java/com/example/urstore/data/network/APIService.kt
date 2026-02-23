package com.example.urstore.data.network

import com.example.urstore.data.model.auth_dto.register.RegisterDto
import com.example.urstore.data.model.auth_dto.register.RegisterRequest
import com.example.urstore.data.model.drinks_dto.DrinksDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

    @GET("products/GetAllDrinks")
    suspend fun allDrinks(
        @Query("pageIndex") pageIndex: Int = 1
    ): DrinksDto

    @POST("account/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterDto
}