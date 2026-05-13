package com.example.urstore.presentation.profile

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStoreRepo
) : BaseViewModel<ProfileIntent>() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        isUserLoggedIn()
    }

    override fun onIntent(intent: ProfileIntent) {
        if (intent is ProfileIntent.Logout) {
            logout()
        } else if (intent is ProfileIntent.ShowDialog) {
            editDialogVisibility(intent.isActive)
        }
    }


    private fun logout() {
        viewModelScope.launch {
            dataStore.clearAllData()
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

    private fun isUserLoggedIn() {
        viewModelScope.launch {
            val token = dataStore.readString(TOKEN).first()
            if (token == "") {
                _uiState.update {
                    it.copy(
                        isUserLoggedIn = false,
                        authName = "Login",
                        authCaption = "Login to your account"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isUserLoggedIn = true,
                        authName = "Log Out",
                        authCaption = "Sign out form your account"
                    )
                }
            }
        }
    }
}