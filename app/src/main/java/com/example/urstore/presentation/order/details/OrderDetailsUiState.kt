package com.example.urstore.presentation.order.details

import com.example.urstore.data.model.orders.details.OrderDetailsResponse
import com.example.urstore.util.RequestState

data class OrderDetailsUiState(
    val orderDetailsState: RequestState = RequestState.IDLE,
    val orderDetailsResponse: OrderDetailsResponse? = OrderDetailsResponse()
)