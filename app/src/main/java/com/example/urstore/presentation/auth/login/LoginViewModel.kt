package com.example.urstore.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.AuthField
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : BaseViewModel<LoginIntent>() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateTextField -> updateTextField(intent.textFieldType, intent.value)
            is LoginIntent.Login -> checkUserInput()
            is LoginIntent.Skip -> sendEffect(UiEffect.ClearBackStack(Screen.HOME_SCREEN.route))
            is LoginIntent.SignUp -> sendEffect(UiEffect.Navigate(Screen.SIGNUP_SCREEN.route))
            is LoginIntent.ForgotPassword -> {
                sendEffect(UiEffect.Navigate(Screen.FORGOT_PASSWORD_SCREEN.route))
            }
        }
    }

    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }


    private fun updateTextField(
        textFieldType: AuthField,
        value: String
    ) {
        viewModelScope.launch {
            when (textFieldType) {
                AuthField.EMAIL -> {
                    _uiState.update {
                        it.copy(email = value)
                    }
                }

                AuthField.PASSWORD -> {
                    _uiState.update {
                        it.copy(password = value)
                    }
                }

                else -> Unit
            }
        }
    }


    private fun checkUserInput() {
        viewModelScope.launch {
            updateState(RequestState.LOADING)

            if (uiState.value.email.isNotEmpty() &&
                uiState.value.password.isNotEmpty()
            ) {
                login()
            } else {
                updateState(RequestState.ERROR)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = "Email and password are required.",
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }


    private fun login() {
        viewModelScope.launch {
            val response = authRepo.login(
                uiState.value.email,
                uiState.value.password
            )

            if (response is Resource.Success) {
                updateState(RequestState.SUCCESS)
                authRepo.saveUserData(response.data)
                sendEffect(UiEffect.ClearBackStack(Screen.HOME_SCREEN.route))

            } else if (response is Resource.Failure) {
                updateState(RequestState.ERROR)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            }
        }
    }

    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(loginState = state)
        }
    }
}