package com.example.urstore.data.repository

import com.example.urstore.data.local.Constants.ADDRESS
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.DISPLAY_NAME
import com.example.urstore.data.local.Constants.EMAIL
import com.example.urstore.data.local.Constants.F_NAME_KEY
import com.example.urstore.data.local.Constants.L_NAME_KEY
import com.example.urstore.data.local.Constants.PHONE
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.auth.login.LoginDto
import com.example.urstore.data.model.auth.login.LoginRequest
import com.example.urstore.data.model.auth.password_reset.ForgetPasswordRequest
import com.example.urstore.data.model.auth.password_reset.ResetPasswordRequest
import com.example.urstore.data.model.auth.password_reset.VerifyOtpRequest
import com.example.urstore.data.model.auth.register.RegisterRequest
import com.example.urstore.data.model.auth.update_user.UpdateUserRequest
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import com.example.urstore.util.AuthField
import com.example.urstore.util.DataStoreRepo
import jakarta.inject.Inject

class AuthRepo @Inject constructor(
    private val api: APIService,
    private val dataStore: DataStoreRepo
) : SafeApiCall {

    fun isInputValidOnSignup(
        map: HashMap<AuthField, String>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val name = map[AuthField.NAME]
        val phone = map[AuthField.PHONE]
        val email = map[AuthField.EMAIL]
        val address = map[AuthField.ADDRESS]
        val password = map[AuthField.PASSWORD]
        val confirmPassword = map[AuthField.CONFIRM_PASSWORD]

        if (name?.isNotEmpty() == true && phone?.isNotEmpty() == true &&
            email?.isNotEmpty() == true && address?.isNotEmpty() == true &&
            password?.isNotEmpty() == true && confirmPassword?.isNotEmpty() == true &&
            password == confirmPassword
        ) {
            onSuccess.invoke()
        } else {
            onError.invoke()
        }
    }


    suspend fun signup(
        map: HashMap<AuthField, String>
    ) = safeApiCall {

        val request = RegisterRequest(
            firstName = returnFirstName(map[AuthField.NAME].toString()),
            lastName = returnLastName(map[AuthField.NAME].toString()),
            email = map[AuthField.EMAIL].toString(),
            phoneNumber = map[AuthField.PHONE].toString(),
            address = map[AuthField.ADDRESS].toString(),
            password = map[AuthField.PASSWORD].toString()
        )
        api.register(request)
    }

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
    }

    suspend fun forgetPassword(email: String) = safeApiCall {
        api.forgetPassword(
            ForgetPasswordRequest(email = email)
        )
    }

    suspend fun saveUserByDataStore(loginResponse: LoginDto?) {
        dataStore.apply {
            writeString(F_NAME_KEY, loginResponse?.firstName.orEmpty())
            writeString(L_NAME_KEY, loginResponse?.lastName.orEmpty())
            writeString(DISPLAY_NAME, loginResponse?.displayName.orEmpty())
            writeString(PHONE, loginResponse?.phone.orEmpty())
            writeString(ADDRESS, loginResponse?.address.orEmpty())
            writeString(EMAIL, loginResponse?.email.orEmpty())
            writeString(TOKEN, loginResponse?.token.orEmpty())
            writeString(CLIENT_ID, loginResponse?.clientId.orEmpty())
        }
    }

    suspend fun saveUserByDataStore(
        fullName: String,
        phone: String,
        address: String
    ) {
        dataStore.apply {
            writeString(DISPLAY_NAME, fullName)
            writeString(PHONE, phone)
            writeString(ADDRESS, address)
        }
    }


    fun returnFirstName(fullName: String): String {
        return if (fullName.contains(' ')) {
            fullName.substringBefore(" ")
        } else fullName
    }

    fun returnLastName(fullName: String): String {
        return if (fullName.contains(' ')) {
            fullName.substringAfter(" ")
        } else fullName
    }

    suspend fun verifyOtp(otp: String, email: String) = safeApiCall {
        api.verifyOtp(
            VerifyOtpRequest(
                email = email,
                otp = otp
            )
        )
    }

    suspend fun resetPassword(
        email: String,
        otp: String,
        newPassword: String
    ) = safeApiCall {
        api.resetPassword(
            ResetPasswordRequest(
                email = email,
                Otp = otp,
                NewPassword = newPassword
            )
        )
    }


    suspend fun updateUser(
        token: String,
        email: String,
        fullName: String,
        phoneNumber: String,
        address: String
    ) = safeApiCall {
        val request = UpdateUserRequest(
            phoneNumber = phoneNumber,
            address = address,
            email = email,
            firstName = returnFirstName(fullName),
            lastName = returnLastName(fullName)
        )
        api.updateUser("Bearer $token", request)
    }
}