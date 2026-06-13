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

enum class QuantityOperation(val value: String) {
    PLUS("plus"),
    MINUS("minus")
}

enum class PaymentMethods(val value: String) {
    VISA("visa"),
    MASTERCARD("mastercard"),
    CASH("cash")
}

enum class DeliveryTimeline(val value: String) {
    PREPARING("preparing"),
    ON_THE_WAY("on_way"),
    DELIVERED("delivered")
}