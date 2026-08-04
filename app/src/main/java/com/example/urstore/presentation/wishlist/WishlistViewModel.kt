package com.example.urstore.presentation.wishlist

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.CoffeeEntity
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.WishlistRepo
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
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
class WishlistViewModel @Inject constructor(
    private val wishlistRepo: WishlistRepo,
    private val cartRepo: CartRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<WishlistIntent>() {

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        displayAllWishlist()
    }

    override fun onIntent(intent: WishlistIntent) {
        when (intent) {
            is WishlistIntent.RemoveItem -> removeItem(intent.item)
            is WishlistIntent.DeleteAll -> deleteAll()
            is WishlistIntent.AddToCart -> addToCart(intent.id)
            is WishlistIntent.ShowDialog -> editDialogVisibility(intent.isActive)
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    private fun addToCart(id: Int) {
        viewModelScope.launch {
            updateLoadingState(true, id)

            val response = cartRepo.initAddToCart(
                drinkId = id,
                count = 1,
                token = dataStore.readString(TOKEN).first(),
                userId = dataStore.readString(CLIENT_ID).first()
            )

            if (response is Resource.Success) {
                updateLoadingState(false, id)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = "Added to cart",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )


            } else if (response is Resource.Failure) {
                updateLoadingState(false, id)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }

    // Responsible for updating {Loading Indicator} for add to cart button
    fun updateLoadingState(
        isLoading: Boolean,
        id: Int
    ) {
        _uiState.update { state ->
            state.copy(
                drinks = state.drinks.map { item ->
                    if (id == item.id) item.copy(isLoading = isLoading)
                    else item
                }
            )
        }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            wishlistRepo.deleteAll()
            _uiState.update {
                it.copy(drinks = emptyList())
            }
        }
    }


    private fun removeItem(item: CoffeeEntity) {
        viewModelScope.launch {
            wishlistRepo.delete(item)

            _uiState.update {
                it.copy(
                    drinks = it.drinks.filter { drink ->
                        drink.id != item.id
                    }
                )
            }
        }
    }

    private fun displayAllWishlist() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    drinks = wishlistRepo.displayWishlist()
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
}