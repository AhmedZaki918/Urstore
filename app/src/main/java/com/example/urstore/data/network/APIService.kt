package com.example.urstore.data.network

import com.example.urstore.data.model.auth_dto.login.LoginDto
import com.example.urstore.data.model.auth_dto.login.LoginRequest
import com.example.urstore.data.model.auth_dto.register.RegisterDto
import com.example.urstore.data.model.auth_dto.register.RegisterRequest
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

    @GET("products/GetAllDrinks")
    suspend fun allDrinks(
        @Query("pageIndex") pageIndex: Int = 1
    ): Response<BaseResponse<List<DrinksDataDto>>>

    @POST("account/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<BaseResponse<RegisterDto>>

    @POST("account/login")
    suspend fun login(
        @Body body: LoginRequest
    ) : Response<BaseResponse<LoginDto>>
}