package com.example.urstore.presentation.see_all

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.SeeAllRepo
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
class SeeAllViewModel @Inject constructor(
    private val seeAllRepo: SeeAllRepo
) : BaseViewModel<SeeAllIntent>() {

    private val _uiState = MutableStateFlow(SeeAllUiState())
    val uiState: StateFlow<SeeAllUiState> = _uiState.asStateFlow()

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
            initLoading()
            val homeResponse = seeAllRepo.getAllDrinks()

            if (homeResponse is Resource.Success) {
                _uiState.update {
                    it.copy(
                        seeAllResponse = homeResponse.data,
                        seeAllState = RequestState.SUCCESS
                    )
                }

            } else {
                _uiState.update {
                    it.copy(
                        seeAllState = RequestState.ERROR
                    )
                }
            }
        }
    }

    private fun addToCart(item: DrinksDataDto) {
    }

    private fun initLoading() {
        _uiState.update {
            it.copy(
                seeAllState = RequestState.LOADING
            )
        }
    }
}
