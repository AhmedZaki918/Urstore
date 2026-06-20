package com.example.urstore.presentation.order.orders

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.orders.OrderState
import com.example.urstore.data.repository.OrdersRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.CurrentOrder
import com.example.urstore.util.DeliveryTimeline
import com.example.urstore.util.OrdersStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersRepo: OrdersRepo
) : BaseViewModel<OrdersIntent>() {

    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    init {
        displayOrderStatusBar()
        updateOrdersList(OrdersStatus.ALL_ORDERS.value)
    }


    override fun onIntent(intent: OrdersIntent) {
        when (intent) {
            is OrdersIntent.OnSortClicked -> sortOrders(intent.id)
        }
    }


    private fun displayOrderStatusBar() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    orderState = orderStatusData()
                )
            }
        }
    }

    private fun sortOrders(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    orderState = it.orderState.map { item ->
                        if (item.id == id) item.copy(isPressed = true)
                        else item.copy(isPressed = false)
                    })
            }

            when (id) {
                101 -> updateOrdersList(OrdersStatus.ALL_ORDERS.value)
                102 -> updateOrdersList(OrdersStatus.ONGOING.value)
                103 -> updateOrdersList(OrdersStatus.COMPLETED.value)
                else -> updateOrdersList(OrdersStatus.CANCELLED.value)
            }
        }
    }


    private fun orderStatusData(): List<OrderState> {
        val status = ArrayList<OrderState>()
        status.add(
            OrderState(
                isPressed = true,
                id = 101,
                title = "All Orders"
            )
        )

        status.add(
            OrderState(
                id = 102,
                title = "Ongoing"
            )
        )

        status.add(
            OrderState(
                id = 103,
                title = "Completed"
            )
        )

        status.add(
            OrderState(
                id = 104,
                title = "Cancelled"
            )
        )

        return status
    }


    private fun updateOrdersList(orderStatus: String) {
        viewModelScope.launch {
            val orders = ArrayList<CurrentOrder>()

            when (orderStatus) {
                OrdersStatus.ALL_ORDERS.value -> {
                    _uiState.update {
                        it.copy(ordersSorted = uiState.value.orders)
                    }
                }

                OrdersStatus.ONGOING.value -> {
                    for (index in uiState.value.orders.indices) {
                        if (uiState.value.orders[index].statusCaption == DeliveryTimeline.PREPARING.value ||
                            uiState.value.orders[index].statusCaption == DeliveryTimeline.ON_THE_WAY.value
                        ) {
                            orders.add(uiState.value.orders[index])
                        }
                    }

                    _uiState.update {
                        it.copy(ordersSorted = orders)
                    }
                }

                OrdersStatus.COMPLETED.value -> {
                    for (index in uiState.value.orders.indices) {
                        if (uiState.value.orders[index].statusCaption == DeliveryTimeline.DELIVERED.value
                        ) {
                            orders.add(uiState.value.orders[index])
                        }
                    }

                    _uiState.update {
                        it.copy(ordersSorted = orders)
                    }
                }

                else -> {
                    for (index in uiState.value.orders.indices) {
                        if (uiState.value.orders[index].statusCaption == DeliveryTimeline.CANCELLED.value
                        ) {
                            orders.add(uiState.value.orders[index])
                        }
                    }

                    _uiState.update {
                        it.copy(ordersSorted = orders)
                    }
                }
            }
        }
    }
}