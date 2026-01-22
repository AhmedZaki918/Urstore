package com.example.urstore.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.HomePopular
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.homeCategoriesDummy
import com.example.urstore.util.homePopularDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cartRepo: CartRepo
) : BaseViewModel<HomeIntent>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        displayCategories()
        displayPopular()
    }


    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnCategoryClicked -> setCategoryActive(intent.id)
            is HomeIntent.AddToCart -> addToCart(intent.item)
            is HomeIntent.RevertAddedToCartStateToIdle -> {
                _uiState.update {
                    it.copy(
                        addedToCartState = RequestState.IDLE
                    )
                }
            }
        }
    }


    private fun displayCategories() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    homeCategories = homeCategoriesDummy()
                )
            }
        }
    }

    private fun displayPopular() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    homePopular = homePopularDummy()
                )
            }
        }
    }

    private fun setCategoryActive(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    homeCategories = it.homeCategories.map { category ->
                        if (category.id == categoryId) {
                            category.copy(isClicked = true)
                        } else {
                            category.copy(isClicked = false)
                        }
                    })
            }
        }
    }


    private fun addToCart(item: HomePopular) {
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
