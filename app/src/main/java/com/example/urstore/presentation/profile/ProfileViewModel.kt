package com.example.urstore.presentation.profile

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        isUserLoggedIn()
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.Logout -> {
                clearUserData()
                sendEffect(UiEffect.ClearBackStack(Screen.LOGIN_SCREEN.route))
            }

            is ProfileIntent.Login -> sendEffect(UiEffect.Navigate(Screen.LOGIN_SCREEN.route))
            is ProfileIntent.GoBack -> sendEffect(UiEffect.PobBackStack)
            is ProfileIntent.ShowDialog -> editDialogVisibility(intent.isActive)
            is ProfileIntent.ChangePassword -> sendEffect(
                UiEffect.Navigate(Screen.FORGOT_PASSWORD_SCREEN.route)
            )

            is ProfileIntent.EditProfile -> {
                if (uiState.value.isUserLoggedIn){
                    sendEffect(UiEffect.Navigate(Screen.EDIT_PROFILE_SCREEN.route))
                } else {
                    editDialogVisibility(true)
                }
            }
        }
    }


    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }


    private fun clearUserData() {
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