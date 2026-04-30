package com.example.urstore.presentation.details

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.CartRepoTest
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.QuantityOperation
import com.example.urstore.util.RequestState
import com.example.urstore.util.UiEffect
import com.example.urstore.util.productSizeDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val cartRepoTest: CartRepoTest,
    private val cartRepo: CartRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<DetailsIntent>() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()


    init {
        displayProductSize()
        prepareTokenAndClientId()
    }


    override fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnSizeClicked -> setSizeActive(intent.id)
            is DetailsIntent.AddToCart -> addToCart(intent.drinkId)
            is DetailsIntent.UpdateQuantity -> updateProductQuantity(intent.operation)
        }
    }


    private fun displayProductSize() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    productSize = productSizeDummy()
                )
            }
        }
    }


    private fun updateProductQuantity(operation: String) {
        viewModelScope.launch {
            if (operation == QuantityOperation.PLUS.value) {
                _uiState.update {
                    it.copy(quantity = it.quantity + 1)
                }

            } else {
                _uiState.update {
                    it.copy(quantity = it.quantity - 1)
                }
            }
        }
    }


    private fun setSizeActive(sizeId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    productSize = it.productSize.map { size ->
                        if (size.id == sizeId) {
                            size.copy(isPressed = true)
                        } else {
                            size.copy(isPressed = false)
                        }
                    })
            }
        }
    }


    private fun addToCart(drinkId: Int) {
        viewModelScope.launch {
            updateState(RequestState.LOADING)
            val response = cartRepo.initAddToCart(
                drinkId = drinkId,
                token = uiState.value.token,
                count = uiState.value.quantity,
                userId = uiState.value.clientId
            )

            if (response is Resource.Success) {
                updateState(RequestState.SUCCESS)
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "Added to cart",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )

            } else if (response is Resource.Failure) {
                updateState(RequestState.ERROR)
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }

    private fun prepareTokenAndClientId() {
        viewModelScope.launch {
            val clientId = async {
                dataStore.readString(CLIENT_ID).collectLatest { id ->
                    _uiState.update {
                        it.copy(clientId = id)
                    }
                }
            }


            val token = async {
                dataStore.readString(TOKEN).collectLatest { userToken ->
                    _uiState.update {
                        it.copy(token = userToken)
                    }
                }
            }

            clientId.await()
            token.await()
        }
    }

    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(addToCartState = state)
        }
    }
}
