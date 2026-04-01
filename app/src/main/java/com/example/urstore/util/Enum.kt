package com.example.urstore.util

enum class RequestState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}

enum class AuthField {
    NAME,
    EMAIL,
    PHONE,
    ADDRESS,
    PASSWORD,
    CONFIRM_PASSWORD
}

enum class ActionLabel(val value: String) {
    SUCCESS("SUCCESS"),
    ERROR("ERROR")
}