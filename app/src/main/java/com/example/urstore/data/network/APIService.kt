package com.example.urstore.data.network

import com.example.urstore.data.model.auth_dto.login.LoginDto
import com.example.urstore.data.model.auth_dto.login.LoginRequest
import com.example.urstore.data.model.auth_dto.register.RegisterDto
import com.example.urstore.data.model.auth_dto.register.RegisterRequest
import com.example.urstore.data.model.cart.DeleteCartDto
import com.example.urstore.data.model.cart.ItemQuantity
import com.example.urstore.data.model.cart.add.AddCartDto
import com.example.urstore.data.model.cart.add.AddCartRequest
import com.example.urstore.data.model.cart.get.CartDto
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.model.password_reset.ForgetPasswordDto
import com.example.urstore.data.model.password_reset.ForgetPasswordRequest
import com.example.urstore.data.model.password_reset.ResetPasswordDto
import com.example.urstore.data.model.password_reset.ResetPasswordRequest
import com.example.urstore.data.model.password_reset.VerifyOtpDto
import com.example.urstore.data.model.password_reset.VerifyOtpRequest
import com.example.urstore.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("products/GetAllDrinks")
    suspend fun allDrinks(
        @Query("pageIndex") pageIndex: Int
    ): Response<BaseResponse<List<DrinksDataDto>>>


    // Authentication apis
    @POST("account/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<BaseResponse<RegisterDto>>

    @POST("account/login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<BaseResponse<LoginDto>>

    @POST("account/forgot-password")
    suspend fun forgetPassword(
        @Body body: ForgetPasswordRequest
    ): Response<BaseResponse<ForgetPasswordDto>>

    @POST("account/verify-otp")
    suspend fun verifyOtp(
        @Body body: VerifyOtpRequest
    ) : Response<BaseResponse<VerifyOtpDto>>

    @POST("account/reset-password")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ) : Response<BaseResponse<ResetPasswordDto>>



    // Cart apis
    @POST("Cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body body: AddCartRequest
    ): Response<BaseResponse<AddCartDto>>

    @GET("Cart/GetAll")
    suspend fun cartItems(
        @Header("Authorization") token: String
    ) :Response<BaseResponse<CartDto>>

    @POST("Cart/removeall")
    suspend fun deleteCart(
        @Header("Authorization") token: String
    ) :Response<BaseResponse<DeleteCartDto>>

    @POST("Cart/remove/{id}")
    suspend fun removeFromCart(
        @Path("id") cartId: Int,
        @Header("Authorization") token: String
    ) :Response<BaseResponse<CartDto>>

    @POST("Cart/increase/{id}")
    suspend fun increaseQuantity(
        @Path("id") cartId: Int,
        @Header("Authorization") token: String
    ) : Response<BaseResponse<CartDto>>

    @POST("Cart/decrease/{id}")
    suspend fun decreaseQuantity(
        @Path("id") cartId: Int,
        @Header("Authorization") token: String
    ) : Response<BaseResponse<CartDto>>
}