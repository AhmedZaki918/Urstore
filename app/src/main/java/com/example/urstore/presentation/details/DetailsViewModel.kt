package com.example.urstore.presentation.details

import androidx.lifecycle.viewModelScope
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.homePopularDummy
import com.example.urstore.util.productSizeDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor() : BaseViewModel<DetailsIntent>() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        displayProductSize()
    }


    override fun onIntent(intent: DetailsIntent) {
        if (intent is DetailsIntent.OnSizeClicked) {
            setSizeActive(intent.id)
        } else if (intent is DetailsIntent.DisplayProductDetails){
            displayProductDetails(intent.id)
        }
    }


    private fun displayProductDetails(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    popularItem = homePopularDummy()[id]
                )
            }
        }
    }


    private fun displayProductSize() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    productSize = productSizeDummy()
                )
            }
        }
    }


    private fun setSizeActive(sizeId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    productSize = it.productSize.map { size ->
                        if (size.id == sizeId) {
                            size.copy(isPressed = true)
                        } else {
                            size.copy(isPressed = false)
                        }
                    })
            }
        }
    }
}
