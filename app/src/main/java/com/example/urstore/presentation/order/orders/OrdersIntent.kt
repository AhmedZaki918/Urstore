package com.example.urstore.presentation.order.orders

sealed class OrdersIntent {
    data class OnSortClicked(
        val id: Int
    ) : OrdersIntent()
}