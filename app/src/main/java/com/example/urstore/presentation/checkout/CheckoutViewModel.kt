package com.example.urstore.presentation.checkout

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.ADDRESS
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.PaymentMethods
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
    private val authRepo: AuthRepo
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
            else -> Unit
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
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
}