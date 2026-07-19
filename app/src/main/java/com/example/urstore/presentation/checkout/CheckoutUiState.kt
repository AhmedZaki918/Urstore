package com.example.urstore.presentation.checkout

import com.example.urstore.data.model.orders.PlaceOrderResponse
import com.example.urstore.util.PaymentMethods
import com.example.urstore.util.RequestState

data class CheckoutUiState(
    val placeOrderState : RequestState = RequestState.IDLE,
    val placeOrderResponse : PlaceOrderResponse? = PlaceOrderResponse(),
    var isDialogActive : Boolean = false,
    val deliveryAddress : String = "",
    var paymentMethods: String = PaymentMethods.VISA.value
)
