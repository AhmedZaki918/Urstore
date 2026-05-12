package com.example.urstore.presentation.cart

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.cart.get.CartDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
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
    private val cartRepo: CartRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<CartIntent>() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        isUserLoggedIn()
    }

    override fun onIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.RemoveItem -> removeFromCart(intent.cartId)
            is CartIntent.IncreaseQuantity -> increaseQuantity(intent.id)
            is CartIntent.DecreaseQuantity -> decreaseQuantity(intent.id)
            is CartIntent.DeleteCart -> deleteCart()
            is CartIntent.ShowDialog -> editDialogVisibility(intent.isActive)
            is CartIntent.RetryFetchCart -> fetchCart()
        }
    }


    private fun isUserLoggedIn() {
        viewModelScope.launch {
            val token = dataStore.readString(TOKEN).first()
            if (token == "") updateCartState(RequestState.SUCCESS)
            else fetchCart()
        }
    }


    private fun fetchCart() {
        viewModelScope.launch {
            updateCartState(RequestState.LOADING)
            val token = dataStore.readString(TOKEN).first()
            val response = cartRepo.initCartItems(token)

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(cartResponse = response.data)
                }
                updateCartState(RequestState.SUCCESS)

            } else if (response is Resource.Failure) {
                updateCartState(RequestState.ERROR)
            }
        }
    }

    private fun deleteCart() {
        viewModelScope.launch {
            updateCartState(RequestState.LOADING)
            val response = cartRepo.initDeleteCart(
                dataStore.readString(TOKEN).first()
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(cartResponse = CartDto())
                }
                updateCartState(RequestState.SUCCESS)

            } else if (response is Resource.Failure) {
                updateCartState(RequestState.ERROR)
            }
        }
    }


    private fun removeFromCart(cartId: Int) {
        viewModelScope.launch {
            updateRemoveState(true, cartId)

            val response = cartRepo.initRemoveFromCart(
                token = dataStore.readString(TOKEN).first(),
                cartId = cartId
            )

            if (response is Resource.Success) {
                updateRemoveState(false, cartId)
                _uiState.update {
                    it.copy(cartResponse = response.data)
                }

            } else if (response is Resource.Failure) {
                updateRemoveState(false, cartId)
                updateCartState(RequestState.ERROR)
            }
        }
    }


    private fun increaseQuantity(cartId: Int) {
        viewModelScope.launch {
            updatePlusState(true, cartId)

            val response = cartRepo.initIncreaseQty(
                token = dataStore.readString(TOKEN).first(),
                cartId = cartId
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        cartResponse = it.cartResponse?.copy(
                            shoppingCartList = it.cartResponse.shoppingCartList.mapIndexed { index, item ->
                                if (cartId == item.cartId) item.copy(
                                    plusIconState = false,
                                    count = response.data?.shoppingCartList[index]?.count,
                                    totalPrice = response.data?.shoppingCartList[index]?.totalPrice
                                )
                                else item
                            },
                            totalAmount = response.data?.totalAmount
                        )
                    )
                }

            } else if (response is Resource.Failure) {
                updatePlusState(false, cartId)
                updateCartState(RequestState.ERROR)
            }
        }
    }


    private fun decreaseQuantity(cartId: Int) {
        viewModelScope.launch {
            updateMinusState(true, cartId)

            val response = cartRepo.initDecreaseQty(
                token = dataStore.readString(TOKEN).first(),
                cartId = cartId
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        cartResponse = it.cartResponse?.copy(
                            shoppingCartList = it.cartResponse.shoppingCartList.mapIndexed { index, item ->
                                if (cartId == item.cartId) item.copy(
                                    minusIconState = false,
                                    count = response.data?.shoppingCartList[index]?.count,
                                    totalPrice = response.data?.shoppingCartList[index]?.totalPrice
                                )
                                else item
                            },
                            totalAmount = response.data?.totalAmount
                        )
                    )
                }

            } else if (response is Resource.Failure) {
                updateMinusState(false, cartId)
                updateCartState(RequestState.ERROR)
            }
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


    private fun updateCartState(state: RequestState) {
        _uiState.update {
            it.copy(cartState = state)
        }
    }


    fun updatePlusState(
        isLoading: Boolean,
        id: Int
    ) {
        _uiState.update { state ->
            state.copy(
                cartResponse = state.cartResponse?.copy(
                    shoppingCartList = state.cartResponse.shoppingCartList.map { item ->
                        if (id == item.cartId) item.copy(plusIconState = isLoading)
                        else item
                    }
                )
            )
        }
    }


    fun updateMinusState(
        isLoading: Boolean,
        id: Int
    ) {
        _uiState.update { state ->
            state.copy(
                cartResponse = state.cartResponse?.copy(
                    shoppingCartList = state.cartResponse.shoppingCartList.map { item ->
                        if (id == item.cartId) item.copy(minusIconState = isLoading)
                        else item
                    }
                )
            )
        }
    }


    fun updateRemoveState(
        isLoading: Boolean,
        id: Int
    ) {
        _uiState.update { state ->
            state.copy(
                cartResponse = state.cartResponse?.copy(
                    shoppingCartList = state.cartResponse.shoppingCartList.map { item ->
                        if (id == item.cartId) item.copy(removeIconState = isLoading)
                        else item
                    }
                )
            )
        }
    }
}