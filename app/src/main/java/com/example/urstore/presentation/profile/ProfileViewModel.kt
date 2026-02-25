package com.example.urstore.presentation.profile

import androidx.lifecycle.viewModelScope
import com.example.urstore.presentation.home.HomeUiState
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStoreRepo
) : BaseViewModel<ProfileIntent>() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: ProfileIntent) {
        if (intent is ProfileIntent.Logout) {
            logout()
        }
    }


    private fun logout() {
        viewModelScope.launch {
            dataStore.clearAllData()
        }
    }
}