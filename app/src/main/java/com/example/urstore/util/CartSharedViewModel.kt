package com.example.urstore.util

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.cart.get.CartDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartSharedViewModel @Inject constructor(
) : BaseViewModel<CartIntentSharedVM>() {

    private val _cartItems = MutableStateFlow(CartDto())
    val cartItems: StateFlow<CartDto> = _cartItems.asStateFlow()

    override fun onIntent(intent: CartIntentSharedVM) {
        if (intent is CartIntentSharedVM.SaveCartItems) {
            saveCart(intent.cartItems)
        }
    }

    private fun saveCart(cart: CartDto) {
        viewModelScope.launch {
            _cartItems.update {
                it.copy(
                    shoppingCartList = cart.shoppingCartList,
                    totalAmount = cart.totalAmount
                )
            }
        }
    }
}