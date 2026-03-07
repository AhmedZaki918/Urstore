package com.example.urstore.data.network

import com.example.urstore.util.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

interface SafeApiCall {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<BaseResponse<T>>
    ): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()

                if (response.isSuccessful) {
                    // (200 - 299)
                    val body = response.body() ?: return@withContext Resource.Failure(
                        true,
                        null,
                        null
                    )

                    val data = body.data ?: return@withContext Resource.Failure(
                        true,
                        response.code(),
                        null,
                        body.message
                    )

                    Resource.Success(data)

                } else {
                    // Non 200 ex: (400, 401, 500)
                    Resource.Failure(
                        true,
                        response.code(),
                        response.errorBody(),
                        errorMessage(response.errorBody()?.string()) ?: response.message()
                    )
                }

            } catch (throwable: Throwable) {
                // Error cases or exceptions occurs
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody(),
                            errorMessage(throwable.response()?.errorBody()?.string())
                                ?: throwable.message()
                        )
                    }

                    else -> {
                        Resource.Failure(
                            true,
                            null,
                            null,
                            throwable.message ?: "Unknown Error"
                        )
                    }
                }
            }
        }
    }


    private fun errorMessage(errorString: String?): String? {
        var error: String? = null
        try {
            if (!errorString.isNullOrEmpty()) {
                val jsonObject = JSONObject(errorString)
                error = jsonObject.getString("message")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return error
    }
}