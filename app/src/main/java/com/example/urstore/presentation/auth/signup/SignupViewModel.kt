package com.example.urstore.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.AuthField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : BaseViewModel<SignupIntent>() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()


    override fun onIntent(intent: SignupIntent) {
        when (intent) {
            is SignupIntent.UpdateTextField -> updateTextField(intent.textFieldType, intent.value)
            is SignupIntent.Signup -> checkUserInput()
            is SignupIntent.ClearErrorState -> updateState(RequestState.IDLE)
        }
    }


    fun updateTextField(
        textFieldType: AuthField,
        value: String
    ) {
        viewModelScope.launch {
            when (textFieldType) {
                AuthField.NAME -> {
                    _uiState.update {
                        it.copy(fullName = value)
                    }
                }

                AuthField.EMAIL -> {
                    _uiState.update {
                        it.copy(email = value)
                    }
                }

                AuthField.PHONE -> {
                    _uiState.update {
                        it.copy(phoneNumber = value)
                    }
                }

                AuthField.ADDRESS -> {
                    _uiState.update {
                        it.copy(address = value)
                    }
                }

                AuthField.PASSWORD -> {
                    _uiState.update {
                        it.copy(password = value)
                    }
                }

                AuthField.CONFIRM_PASSWORD -> {
                    _uiState.update {
                        it.copy(confirmPassword = value)
                    }
                }
            }
        }
    }


    fun checkUserInput() {
        viewModelScope.launch {
            updateState(RequestState.LOADING)
            val hashMap = HashMap<AuthField, String>()

            uiState.value.apply {
                hashMap[AuthField.NAME] = fullName
                hashMap[AuthField.EMAIL] = email
                hashMap[AuthField.PHONE] = phoneNumber
                hashMap[AuthField.ADDRESS] = address
                hashMap[AuthField.PASSWORD] = password
                hashMap[AuthField.CONFIRM_PASSWORD] = confirmPassword
            }

            authRepo.isInputValidOnSignup(
                map = hashMap,
                onSuccess = {
                    signup(hashMap)
                },
                onError = {
                    updateState(RequestState.ERROR)
                }
            )
        }
    }


    private fun signup(hashmap: HashMap<AuthField, String>) {
        viewModelScope.launch {
            val response = authRepo.signup(hashmap)

            if (response is Resource.Success) {
                updateState(RequestState.SUCCESS)
            } else {
                updateState(RequestState.ERROR)
            }
        }
    }


    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(signupState = state)
        }
    }
}