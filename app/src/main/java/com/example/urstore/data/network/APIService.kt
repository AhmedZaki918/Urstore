package com.example.urstore.data.network

import com.example.urstore.data.model.drinks_dto.DrinksDto
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("products/GetAllDrinks")
    suspend fun allDrinks(
        @Query("pageIndex") pageIndex: Int = 1
    ): DrinksDto
}