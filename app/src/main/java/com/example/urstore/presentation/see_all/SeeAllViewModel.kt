package com.example.urstore.presentation.see_all

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.SeeAllRepo
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
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

    private val _uiState = MutableStateFlow(SeeAllUiState())
    val uiState: StateFlow<SeeAllUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        isUserLoggedIn()
        displaySeeAll()
    }

    override fun onIntent(intent: SeeAllIntent) {
        when (intent) {
            is SeeAllIntent.AddToCart -> {
                if (uiState.value.isUserLoggedIn) addToCart(intent.item)
                else editDialogVisibility(true)
            }

            is SeeAllIntent.ShowDialog -> editDialogVisibility(intent.isActive)
            is SeeAllIntent.Login -> sendEffect(UiEffect.Navigate(Screen.LOGIN_SCREEN.route))
            is SeeAllIntent.GoToDetails -> sendEffect(UiEffect.Navigate(Screen.DETAIL_SCREEN.route))
            is SeeAllIntent.GoBack -> sendEffect(UiEffect.PobBackStack)
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch {
            val token = dataStore.readString(TOKEN).first()
            if (token == "") {
                _uiState.update {
                    it.copy(isUserLoggedIn = false)
                }
            } else {
                _uiState.update {
                    it.copy(isUserLoggedIn = true)
                }
            }
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
                updateLoadingState(false, item.id)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = "Added to cart",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )

            } else if (response is Resource.Failure) {
                updateLoadingState(false, item.id)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }


    // Responsible for updating {Loading Indicator} for add to cart button
    fun updateLoadingState(
        isLoading: Boolean,
        id: Int
    ) {
        _drinks.value = _drinks.value.map { item ->
            if (item.id == id) item.copy(isLoading = isLoading)
            else item
        }
    }

    private fun editDialogVisibility(isActive: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoginDialogActive = isActive
                )
            }
        }
    }
}
