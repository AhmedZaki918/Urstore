package com.example.urstore.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.SignupField
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
            is SignupIntent.UpdateTextField -> {
                updateTextField(
                    intent.textFieldType,
                    intent.value
                )
            }

            is SignupIntent.Signup -> checkUserInput()
            is SignupIntent.ClearErrorState -> updateState(RequestState.IDLE)
        }
    }


    fun updateTextField(
        textFieldType: SignupField,
        value: String
    ) {
        viewModelScope.launch {
            when (textFieldType) {
                SignupField.NAME -> {
                    _uiState.update {
                        it.copy(fullName = value)
                    }
                }

                SignupField.EMAIL -> {
                    _uiState.update {
                        it.copy(email = value)
                    }
                }

                SignupField.PHONE -> {
                    _uiState.update {
                        it.copy(phoneNumber = value)
                    }
                }

                SignupField.ADDRESS -> {
                    _uiState.update {
                        it.copy(address = value)
                    }
                }

                SignupField.PASSWORD -> {
                    _uiState.update {
                        it.copy(password = value)
                    }
                }

                SignupField.CONFIRM_PASSWORD -> {
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
            val hashMap = HashMap<SignupField, String>()

            uiState.value.apply {
                hashMap[SignupField.NAME] = fullName
                hashMap[SignupField.EMAIL] = email
                hashMap[SignupField.PHONE] = phoneNumber
                hashMap[SignupField.ADDRESS] = address
                hashMap[SignupField.PASSWORD] = password
                hashMap[SignupField.CONFIRM_PASSWORD] = confirmPassword
            }

            authRepo.isInputValid(
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


    private fun signup(hashmap: HashMap<SignupField, String>) {
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