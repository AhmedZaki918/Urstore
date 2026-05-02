package com.example.urstore.presentation.see_all

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.SeeAllRepo
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val seeAllRepo: SeeAllRepo,
    private val cartRepo: CartRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<SeeAllIntent>() {

    private val _drinks = MutableStateFlow<PagingData<DrinksDataDto>>(PagingData.empty())
    val drinks: StateFlow<PagingData<DrinksDataDto>> get() = _drinks

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        displaySeeAll()
    }

    override fun onIntent(intent: SeeAllIntent) {
        if (intent is SeeAllIntent.AddToCart) {
            addToCart(intent.item)
        }
    }


    private fun displaySeeAll() {
        viewModelScope.launch {
            seeAllRepo.getAllDrinks()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _drinks.value = pagingData
                }
        }
    }

    private fun addToCart(item: DrinksDataDto) {
        viewModelScope.launch {
            updateLoadingState(true, item.id)
            val response = cartRepo.initAddToCart(
                drinkId = item.id,
                count = 1,
                token = dataStore.readString(TOKEN).first(),
                userId = dataStore.readString(CLIENT_ID).first()
            )

            if (response is Resource.Success) {
                updateLoadingState(false)
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "Added to cart",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )

            } else if (response is Resource.Failure) {
                updateLoadingState(false)
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }


    // Responsible for updating {Loading Indicator State} via id for add to cart button
    fun updateLoadingState(
        isLoading: Boolean,
        id: Int = 0
    ) {
        _drinks.value = _drinks.value.map { item ->
            if (isLoading) {
                if (item.id == id) item.copy(isLoading = true)
                else item.copy(isLoading = false)
            } else {
                item.copy(isLoading = false)
            }
        }
    }
}
