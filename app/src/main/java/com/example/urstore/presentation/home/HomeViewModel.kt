package com.example.urstore.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.CLIENT_ID
import com.example.urstore.data.local.Constants.F_NAME_KEY
import com.example.urstore.data.local.Constants.L_NAME_KEY
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.model.categories.CategoriesDto
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.CartRepo
import com.example.urstore.data.repository.HomeRepo
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.RequestState
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
class HomeViewModel @Inject constructor(
    private val cartRepo: CartRepo,
    private val homeRepo: HomeRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<HomeIntent>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        isUserLoggedIn()
        getUserData()
        loadHomeData()
    }


    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnCategoryClicked -> {
                setCategoryActive(intent.id)
                sendEffect(UiEffect.Navigate(Screen.SEE_ALL_SCREEN.route))
            }

            is HomeIntent.AddToCart -> {
                if (uiState.value.isUserLoggedIn) addToCart(intent.item)
                else editDialogVisibility(true)
            }

            is HomeIntent.RetryHome -> loadHomeData()
            is HomeIntent.ShowDialog -> editDialogVisibility(intent.isActive)
            is HomeIntent.GoToDetails -> sendEffect(UiEffect.Navigate(Screen.DETAIL_SCREEN.route))
            is HomeIntent.Login -> sendEffect(UiEffect.Navigate(Screen.LOGIN_SCREEN.route))
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }


    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(homeState = RequestState.LOADING)
            }
            // Run 3 apis in parallel
            val categoriesDeferred = async { homeRepo.categories() }
            val popularDeferred = async { homeRepo.getAllDrinks() }
            val offersDeferred = async { homeRepo.offers() }

            val categoriesResponse = categoriesDeferred.await()
            val popularResponse = popularDeferred.await()
            val offersResponse = offersDeferred.await()


            if (categoriesResponse is Resource.Success &&
                popularResponse is Resource.Success &&
                offersResponse is Resource.Success
            ) {
                _uiState.update {
                    it.copy(
                        homeCategories = updateCategories(categoriesResponse.data),
                        popularResponse = popularResponse.data,
                        offersResponse = offersResponse.data,
                        homeState = RequestState.SUCCESS
                    )
                }
            } else {
                _uiState.update {
                    it.copy(homeState = RequestState.ERROR)
                }
            }
        }
    }


    private fun updateCategories(homeCategories: List<CategoriesDto>?): List<CategoriesDto>? {
        homeCategories?.get(0)?.isClicked = true
        return homeCategories
    }

    private fun setCategoryActive(categoryId: Int?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    homeCategories = it.homeCategories?.map { category ->
                        if (category.id == categoryId) category.copy(isClicked = true)
                        else category.copy(isClicked = false)
                    })
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

                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "Added to cart",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )

            } else if (response is Resource.Failure) {
                updateLoadingState(false, item.id)

                _effects.emit(
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
        _uiState.update { state ->
            state.copy(
                popularResponse = state.popularResponse?.map { item ->
                    if (id == item.id) item.copy(isLoading = isLoading)
                    else item
                }
            )
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


    private fun editDialogVisibility(isActive: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoginDialogActive = isActive
                )
            }
        }
    }


    private fun getUserData() {
        viewModelScope.launch {
            val firstName = async {
                dataStore.readString(F_NAME_KEY).collectLatest { value ->
                    if (value == "") {
                        _uiState.update {
                            it.copy(firstName = "Guest")
                        }
                    } else {
                        _uiState.update {
                            it.copy(firstName = value)
                        }
                    }
                }
            }

            val lastName = async {
                dataStore.readString(L_NAME_KEY).collectLatest { value ->
                    _uiState.update {
                        it.copy(lastName = value)
                    }
                }
            }

            firstName.await()
            lastName.await()
        }
    }
}