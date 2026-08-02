package com.example.urstore.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.PRODUCT_ID
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.drinks.ItemDetails
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.WishlistRepo
import com.example.urstore.presentation.navigation.Screen
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
    savedStateHandle: SavedStateHandle,
    private val cartRepo: CartRepo,
    private val dataStore: DataStoreRepo,
    private val wishlistRepo: WishlistRepo
) : BaseViewModel<DetailsIntent>() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        savedStateHandle.get<Int>(PRODUCT_ID)?.let {
            isCoffeeSaved(id = it)
        }
        displayProductSize()
        prepareTokenAndClientId()
    }

    override fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnSizeClicked -> setSizeActive(intent.id)
            is DetailsIntent.AddToCart -> {
                if (uiState.value.token != "") addToCart(intent.drinkId)
                else editDialogVisibility(true)
            }

            is DetailsIntent.UpdateQuantity -> updateProductQuantity(intent.operation)
            is DetailsIntent.ShowDialog -> editDialogVisibility(intent.isActive)
            is DetailsIntent.Login -> sendEffect(UiEffect.Navigate(Screen.LOGIN_SCREEN.route))
            is DetailsIntent.GoBack -> sendEffect(UiEffect.PobBackStack)
            is DetailsIntent.AddToWishlist -> addToWishlist(intent.coffee)
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    private fun addToWishlist(coffee: ItemDetails) {
        viewModelScope.launch {
            if (wishlistRepo.isItemSaved(coffee.id)){
                wishlistRepo.delete(coffee)
                _uiState.update {
                    it.copy(isItemOnWishlist = false)
                }
            } else {
                wishlistRepo.addToWishlist(coffee)
                _uiState.update {
                    it.copy(isItemOnWishlist = true)
                }
            }
        }
    }

    private fun isCoffeeSaved(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isItemOnWishlist = wishlistRepo.isItemSaved(id)
                )
            }
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
                    productSize = it.productSize.map { item ->
                        if (item.id == sizeId) item.copy(isPressed = true)
                        else item.copy(isPressed = false)
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

    private fun editDialogVisibility(isActive: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoginDialogActive = isActive
                )
            }
        }
    }
}
