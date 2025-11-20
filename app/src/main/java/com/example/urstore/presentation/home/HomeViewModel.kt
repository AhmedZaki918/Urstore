package com.example.urstore.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.urstore.util.BaseViewModel
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
class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        displayCategories()
        displayPopular()
    }


    override fun onIntent(intent: HomeIntent) {
        if (intent is HomeIntent.OnCategoryClicked) {
            setCategoryActive(intent.id)
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
}