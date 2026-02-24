package com.example.urstore.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStoreRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        getToken()
    }

    fun getToken() {
        viewModelScope.launch {
            dataStore.readString(TOKEN).collectLatest { token ->
                _uiState.update {
                    it.copy(
                        userToken = token,
                        isLoading = false
                    )
                }
            }
        }
    }
}