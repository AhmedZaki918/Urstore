package com.example.urstore.util

import android.os.Message

data class BaseResponse<T>(
    val data : T? = null,
    val message: String? = null
)
