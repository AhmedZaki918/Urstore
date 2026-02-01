package com.example.urstore.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.ItemDetails
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.HomeRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.homeCategoriesDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cartRepo: CartRepo,
    private val homeRepo: HomeRepo
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
            is HomeIntent.OnPopularClicked -> savePopularItem(intent.item)
            is HomeIntent.RevertAddedToCartStateToIdle -> {
                _uiState.update {
                    it.copy(
                        addedToCartState = RequestState.IDLE
                    )
                }
            }
        }
    }

    private fun savePopularItem(item: DrinksDataDto) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    itemDetails = ItemDetails(
                        description = item.description,
                        imageName = item.imageName,
                        id = item.id,
                        rate = item.rate,
                        price = item.price,
                        title = item.title
                    )
                )
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
            initLoading()
            val homeResponse = homeRepo.getAllDrinks()

            if (homeResponse is Resource.Success) {
                _uiState.update {
                    it.copy(
                        popularResponse = homeResponse.data,
                        homeState = RequestState.SUCCESS
                    )
                }

            } else {
                _uiState.update {
                    it.copy(
                        homeState = RequestState.ERROR
                    )
                }
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

    private fun initLoading() {
        _uiState.update {
            it.copy(
                homeState = RequestState.LOADING
            )
        }
    }
}