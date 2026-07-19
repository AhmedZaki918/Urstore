package com.example.urstore.presentation.order.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.ORDER_ID
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.OrdersRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.RequestState
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val ordersRepo: OrdersRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<OrderDetailsIntent>() {

    private val _uiState = MutableStateFlow(OrderDetailsUiState())
    val uiState: StateFlow<OrderDetailsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        savedStateHandle.get<Int>(ORDER_ID)?.let {
            fetchOrderDetails(orderId = it)
        }
    }

    override fun onIntent(intent: OrderDetailsIntent) {
    }

    private fun fetchOrderDetails(orderId: Int?) {
        viewModelScope.launch {
            updateState(RequestState.LOADING)

            val response = ordersRepo.orderDetails(
                token = dataStore.readString(TOKEN).first(),
                orderId = orderId
            )

            if (response is Resource.Success) {
                updateState(RequestState.SUCCESS)
                _uiState.update {
                    it.copy(orderDetailsResponse = response.data)
                }

            } else if (response is Resource.Failure) {
                updateState(RequestState.ERROR)
            }
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(orderDetailsState = state)
        }
    }
}




