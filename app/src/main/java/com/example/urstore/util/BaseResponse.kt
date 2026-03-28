package com.example.urstore.util

data class BaseResponse<T>(
    val data : T? = null,
    val message: String? = null
)
