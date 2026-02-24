package com.example.urstore.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.presentation.auth.signup.SignupIntent
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.AuthField
import com.example.urstore.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateTextField -> updateTextField(intent.textFieldType, intent.value)
            is LoginIntent.Login -> checkUserInput()
            is LoginIntent.ClearErrorState -> updateState(RequestState.IDLE)
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

            authRepo.isInputValidOnLogin(
                email = uiState.value.email,
                password = uiState.value.password,
                onSuccess = {
                    login()
                },
                onError = {
                    updateState(RequestState.ERROR)
                }
            )
        }
    }


    private fun login() {
        viewModelScope.launch {
            val response = authRepo.login(
                uiState.value.email,
                uiState.value.password
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        loginResponse = response.data.data,
                        responseMessage = response.data.message
                    )
                }
                authRepo.saveUserData(response.data.data)
                updateState(RequestState.SUCCESS)
            } else {
                updateState(RequestState.ERROR)
            }
        }
    }


    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(loginState = state)
        }
    }
}