package com.example.urstore.presentation.details

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.UiEffect
import com.example.urstore.util.productSizeDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val cartRepo: CartRepo
) : BaseViewModel<DetailsIntent>() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()


    init {
        displayProductSize()
    }


    override fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnSizeClicked -> setSizeActive(intent.id)
            is DetailsIntent.AddToCart -> addToCart(intent.item)
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


    private fun addToCart(item: DrinksDataDto) {
        viewModelScope.launch {
            if (!cartRepo.isItemInCart(item.id)) {
                cartRepo.addToCart(item)
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "Added to cart",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )


            } else if (cartRepo.isItemInCart(item.id)) {
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "Already exist in cart",
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }
}
