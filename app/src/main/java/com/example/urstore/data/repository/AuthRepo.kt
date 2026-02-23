package com.example.urstore.data.repository

import com.example.urstore.data.model.auth_dto.register.RegisterRequest
import com.example.urstore.data.network.APIService
import com.example.urstore.data.network.SafeApiCall
import com.example.urstore.util.SignupField
import jakarta.inject.Inject

class AuthRepo @Inject constructor(
    private val api: APIService
) : SafeApiCall {

    fun isInputValid(
        map: HashMap<SignupField, String>,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val name = map[SignupField.NAME]
        val phone = map[SignupField.PHONE]
        val email = map[SignupField.EMAIL]
        val address = map[SignupField.ADDRESS]
        val password = map[SignupField.PASSWORD]
        val confirmPassword = map[SignupField.CONFIRM_PASSWORD]

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
        map: HashMap<SignupField, String>
    ) = safeApiCall {

        val request = RegisterRequest(
            firstName = returnFirstName(map[SignupField.NAME].toString()),
            lastName = returnLastName(map[SignupField.NAME].toString()),
            email = map[SignupField.EMAIL].toString(),
            phoneNumber = map[SignupField.PHONE].toString(),
            address = map[SignupField.ADDRESS].toString(),
            password = map[SignupField.PASSWORD].toString()
        )
        api.register(request)
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