package com.example.urstore.presentation.details

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.homePopularDummy
import com.example.urstore.util.productSizeDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        displayProductSize()
    }


    override fun onIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.OnSizeClicked -> setSizeActive(intent.id)
            is DetailsIntent.DisplayProductDetails -> displayProductDetails(intent.id)
            is DetailsIntent.AddToCart -> addToCart(intent.item)
            is DetailsIntent.RevertAddedToCartStateToIdle -> {
                _uiState.update {
                    it.copy(
                        addedToCartState = RequestState.IDLE
                    )
                }
            }
        }
    }


    private fun displayProductDetails(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    popularItem = homePopularDummy()[id]
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
                _uiState.update {
                    it.copy(
                        addedToCartState = RequestState.SUCCESS
                    )
                }
            } else if (cartRepo.isItemInCart(item.id)) {
                _uiState.update {
                    it.copy(
                        addedToCartState = RequestState.ERROR
                    )
                }
            }
        }
    }
}
