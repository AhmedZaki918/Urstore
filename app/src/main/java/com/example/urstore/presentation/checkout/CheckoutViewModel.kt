package com.example.urstore.presentation.checkout

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.ADDRESS
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.DISPLAY_NAME
import com.example.urstore.data.local.Constants.EMAIL
import com.example.urstore.data.local.Constants.PHONE
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.data.repository.OrdersRepo
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.PaymentMethods
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
class CheckoutViewModel @Inject constructor(
    private val dataStore: DataStoreRepo,
    private val authRepo: AuthRepo,
    private val ordersRepo: OrdersRepo
) : BaseViewModel<CheckoutIntent>() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        displayAddress()
    }

    override fun onIntent(intent: CheckoutIntent) {
        when (intent) {
            is CheckoutIntent.GoBack -> sendEffect(UiEffect.PobBackStack)
            is CheckoutIntent.EditCart -> sendEffect(UiEffect.PobBackStack)
            is CheckoutIntent.ShowDialog -> editDialogVisibility(intent.isActive)
            is CheckoutIntent.ChangeAddress -> updateTextField(intent.value)
            is CheckoutIntent.SaveAddress -> saveAddress()
            is CheckoutIntent.CancelAddress -> displayAddress()
            is CheckoutIntent.ChangePayment -> updatePaymentMethods(intent.paymentType)
            is CheckoutIntent.PlaceOrder -> placeOrder()
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    private fun placeOrder() {
        viewModelScope.launch {
            if (uiState.value.paymentMethods != PaymentMethods.CASH.value) {
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = "Credit card in development",
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            } else {
                updateOrderState(RequestState.LOADING)

                val response = ordersRepo.placeOrder(
                    token = dataStore.readString(TOKEN).first(),
                    userId = dataStore.readString(CLIENT_ID).first(),
                    fullName = dataStore.readString(DISPLAY_NAME).first(),
                    email = dataStore.readString(EMAIL).first(),
                    address = dataStore.readString(ADDRESS).first(),
                    phoneNumber = dataStore.readString(PHONE).first()
                )

                if (response is Resource.Success) {
                    updateOrderState(RequestState.SUCCESS)
                    sendEffect(UiEffect.ClearBackStack(Screen.ORDER_DETAILS.route))

                } else if (response is Resource.Failure) {
                    updateOrderState(RequestState.ERROR)
                    sendEffect(UiEffect.ShowSnackbar(
                        message = response.message.toString(),
                        actionLabel = ActionLabel.ERROR.value
                    ))
                }
            }
        }
    }


    private fun saveAddress() {
        viewModelScope.launch {
            authRepo.saveUserByDataStore(uiState.value.deliveryAddress)
        }
    }

    private fun displayAddress() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    deliveryAddress = dataStore.readString(ADDRESS).first()
                )
            }
        }
    }

    private fun editDialogVisibility(isActive: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDialogActive = isActive
                )
            }
        }
    }


    private fun updateTextField(value: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(deliveryAddress = value)
            }
        }
    }

    private fun updatePaymentMethods(paymentType: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(paymentMethods = paymentType)
            }
        }
    }

    private fun updateOrderState(state: RequestState) {
        _uiState.update {
            it.copy(placeOrderState = state)
        }
    }
}