package com.example.urstore.presentation.wishlist

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.CoffeeEntity
import com.example.urstore.data.repository.WishlistRepo
import com.example.urstore.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepo: WishlistRepo
) : BaseViewModel<WishlistIntent>() {

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: WishlistIntent) {
        if (intent is WishlistIntent.RemoveItem) {
            removeItem(intent.item)
        }
    }

    init {
        displayAllWishlist()
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
}