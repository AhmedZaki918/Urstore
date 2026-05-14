package com.example.urstore.data.network

import com.example.urstore.data.model.auth.login.LoginDto
import com.example.urstore.data.model.auth.login.LoginRequest
import com.example.urstore.data.model.auth.register.RegisterDto
import com.example.urstore.data.model.auth.register.RegisterRequest
import com.example.urstore.data.model.cart.DeleteCartDto
import com.example.urstore.data.model.cart.add.AddCartDto
import com.example.urstore.data.model.cart.add.AddCartRequest
import com.example.urstore.data.model.cart.get.CartDto
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.data.model.auth.password_reset.ForgetPasswordDto
import com.example.urstore.data.model.auth.password_reset.ForgetPasswordRequest
import com.example.urstore.data.model.auth.password_reset.ResetPasswordDto
import com.example.urstore.data.model.auth.password_reset.ResetPasswordRequest
import com.example.urstore.data.model.auth.password_reset.VerifyOtpDto
import com.example.urstore.data.model.auth.password_reset.VerifyOtpRequest
import com.example.urstore.data.model.categories.CategoriesDto
import com.example.urstore.data.model.offer.OfferDto
import com.example.urstore.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    // Home apis
    @GET("products/GetAllDrinks")
    suspend fun allDrinks(
        @Query("pageIndex") pageIndex: Int
    ): Response<BaseResponse<List<DrinksDataDto>>>

    @GET("categories/{category}/{id}")
    suspend fun categories(
        @Path("category") category: Int = 2,
        @Path("id") id: Int = 1
    ): Response<BaseResponse<List<CategoriesDto>>>

    @GET("banner")
    suspend fun offers(): Response<BaseResponse<List<OfferDto>>>


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
    ): Response<BaseResponse<VerifyOtpDto>>

    @POST("account/reset-password")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): Response<BaseResponse<ResetPasswordDto>>


    // Cart apis
    @POST("Cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body body: AddCartRequest
    ): Response<BaseResponse<AddCartDto>>

    @GET("Cart/GetAll")
    suspend fun cartItems(
        @Header("Authorization") token: String
    ): Response<BaseResponse<CartDto>>

    @POST("Cart/removeall")
    suspend fun deleteCart(
        @Header("Authorization") token: String
    ): Response<BaseResponse<DeleteCartDto>>

    @POST("Cart/remove/{id}")
    suspend fun removeFromCart(
        @Path("id") cartId: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse<CartDto>>

    @POST("Cart/increase/{id}")
    suspend fun increaseQuantity(
        @Path("id") cartId: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse<CartDto>>

    @POST("Cart/decrease/{id}")
    suspend fun decreaseQuantity(
        @Path("id") cartId: Int,
        @Header("Authorization") token: String
    ): Response<BaseResponse<CartDto>>
}