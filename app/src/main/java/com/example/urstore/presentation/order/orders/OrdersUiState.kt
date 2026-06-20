package com.example.urstore.presentation.order.orders

import com.example.urstore.data.model.orders.OrderState
import com.example.urstore.util.CurrentOrder
import com.example.urstore.util.allOrdersDummy

data class OrdersUiState(
    val orderState: List<OrderState> = emptyList(),
    val orders :  List<CurrentOrder> = allOrdersDummy(),
    val ordersSorted :  List<CurrentOrder> =  emptyList()
)





