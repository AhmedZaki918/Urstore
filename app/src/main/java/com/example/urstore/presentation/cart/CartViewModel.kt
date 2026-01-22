package com.example.urstore.presentation.cart

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.Cart
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepo: CartRepo
) : BaseViewModel<CartIntent>() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        fetchCart()
        calculateSubTotal()
    }

    override fun onIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.RemoveItem -> removeItemFromCart(intent.item)
            is CartIntent.IncreaseQuantity -> increaseQuantity(intent.id)
            is CartIntent.DecreaseQuantity -> decreaseQuantity(intent.id)
        }
    }


    fun fetchCart() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    cartItems = cartRepo.fetchCart()
                )
            }
        }
    }


    fun removeItemFromCart(cartItem: Cart) {
        viewModelScope.launch {
            val updatedCart = uiState.value.cartItems.filter { item ->
                item.id != cartItem.id
            }
            _uiState.update {
                it.copy(
                    deleteState = it.deleteState.plus(
                        Pair(
                            cartItem.id.toString(),
                            RequestState.LOADING
                        )
                    )
                        .toMutableMap(),
                    cartItems = updatedCart,
                    subtotal = it.subtotal - cartItem.totalPrice
                )
            }

            cartRepo.removeItemFromCart(cartItem)
        }
    }

    fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            var unitPrice = 0.0

            _uiState.update {
                it.copy(
                    plusState = it.plusState.plus(Pair(id.toString(), RequestState.LOADING))
                        .toMutableMap(),

                    cartItems = it.cartItems.map { cartItem ->
                        if (cartItem.id == id) {
                            unitPrice = cartItem.unitPrice
                            val updatedQty = cartItem.qty + 1
                            cartItem.copy(
                                qty = updatedQty,
                                totalPrice = (updatedQty * cartItem.unitPrice)
                            )
                        } else cartItem
                    },
                    subtotal = it.subtotal + unitPrice
                )
            }
            cartRepo.increaseQuantity(id)
        }
    }

    fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            var unitPrice = 0.0

            _uiState.update {
                it.copy(
                    minusState = it.minusState.plus(Pair(id.toString(), RequestState.LOADING))
                        .toMutableMap(),

                    cartItems = it.cartItems.map { cartItem ->
                        if (cartItem.id == id && cartItem.qty > 1) {
                            unitPrice = cartItem.unitPrice
                            val updatedQty = cartItem.qty - 1
                            cartItem.copy(
                                qty = updatedQty,
                                totalPrice = (updatedQty * cartItem.unitPrice)

                            )
                        } else cartItem
                    },
                    subtotal = it.subtotal - unitPrice
                )
            }
            cartRepo.decreaseQuantity(id)
        }
    }

    fun calculateSubTotal() {
        viewModelScope.launch {
            var updatedSubTotal = 0.0
            for (item in uiState.value.cartItems) {
                updatedSubTotal += item.totalPrice
            }
            _uiState.update {
                it.copy(
                    subtotal = updatedSubTotal
                )
            }
        }
    }
}