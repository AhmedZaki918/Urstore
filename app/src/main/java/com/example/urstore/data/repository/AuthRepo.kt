package com.example.urstore.data.repository

import com.example.urstore.data.local.Constants.F_NAME_KEY
import com.example.urstore.data.local.Constants.L_NAME_KEY
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.auth_dto.login.LoginData
import com.example.urstore.data.model.auth_dto.login.LoginRequest
import com.example.urstore.data.model.auth_dto.register.RegisterRequest
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import com.example.urstore.util.AuthField
import com.example.urstore.util.DataStoreRepo
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest

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


    fun isInputValidOnLogin(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
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

    suspend fun saveUserData(loginResponse: LoginData) {
        dataStore.writeString(F_NAME_KEY, loginResponse.firstName)
        dataStore.writeString(L_NAME_KEY, loginResponse.lastName)
        dataStore.writeString(TOKEN, loginResponse.token)
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
}