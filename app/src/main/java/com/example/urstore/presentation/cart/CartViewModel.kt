package com.example.urstore.presentation.cart

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.Cart
import com.example.urstore.data.model.cart.get.CartDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.CartRepoTest
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepoTest: CartRepoTest,
    private val cartRepo: CartRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<CartIntent>() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        fetchCart()
    }


    override fun onIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.RemoveItem -> removeItemFromCart(intent.item)
            is CartIntent.IncreaseQuantity -> increaseQuantity(intent.id)
            is CartIntent.DecreaseQuantity -> decreaseQuantity(intent.id)
            is CartIntent.DeleteCart -> deleteCart()
            is CartIntent.ShowDialog -> editDialogVisibility(intent.isActive)
        }
    }


    private fun fetchCart() {
        viewModelScope.launch {
            updateState(RequestState.LOADING)
            val token = dataStore.readString(TOKEN).first()
            val response = cartRepo.initCartItems(token)

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(cartResponse = response.data)
                }
                updateState(RequestState.SUCCESS)

            } else if (response is Resource.Failure) {
                updateState(RequestState.ERROR)
            }
        }
    }

    private fun deleteCart() {
        viewModelScope.launch {
            updateState(RequestState.LOADING)
            val response = cartRepo.initRemoveCart(
                dataStore.readString(TOKEN).first()
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(cartResponse =  CartDto())
                }
                updateState(RequestState.SUCCESS)

            } else if (response is Resource.Failure) {
                updateState(RequestState.ERROR)
            }
        }
    }


    fun removeItemFromCart(cartItem: Cart) {
        viewModelScope.launch {
            val updatedCart = uiState.value.cartItemsTest.filter { item ->
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
                    cartItemsTest = updatedCart,
                    subtotal = it.subtotal - cartItem.totalPrice
                )
            }

            cartRepoTest.removeItemFromCart(cartItem)
        }
    }

    fun increaseQuantity(id: Int) {
        viewModelScope.launch {
            var unitPrice = 0

            _uiState.update {
                it.copy(
                    plusState = it.plusState.plus(Pair(id.toString(), RequestState.LOADING))
                        .toMutableMap(),

                    cartItemsTest = it.cartItemsTest.map { cartItem ->
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
            cartRepoTest.increaseQuantity(id)
        }
    }

    fun decreaseQuantity(id: Int) {
        viewModelScope.launch {
            var unitPrice = 0

            _uiState.update {
                it.copy(
                    minusState = it.minusState.plus(Pair(id.toString(), RequestState.LOADING))
                        .toMutableMap(),

                    cartItemsTest = it.cartItemsTest.map { cartItem ->
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
            cartRepoTest.decreaseQuantity(id)
        }
    }


    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(cartState = state)
        }
    }

    private fun editDialogVisibility(isActive: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCartDialogActive = isActive
                )
            }
        }
    }
}