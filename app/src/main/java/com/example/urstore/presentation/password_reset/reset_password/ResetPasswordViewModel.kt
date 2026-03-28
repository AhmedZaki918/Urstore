package com.example.urstore.presentation.password_reset.reset_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.EMAIL_ADDRESS
import com.example.urstore.data.local.Constants.OTP
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.util.AuthField
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepo: AuthRepo
) : BaseViewModel<ResetPasswordIntent>() {

    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState.asStateFlow()

    init {
        val email = savedStateHandle.get<String>(EMAIL_ADDRESS)
        val otp = savedStateHandle.get<String>(OTP)

        if (email != null && otp != null) {
            saveOtpAndEmail(email, otp)
        }
    }

    override fun onIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.UpdateTextField ->
                updateTextField(intent.textFieldType, intent.value)

            is ResetPasswordIntent.ResetPassword -> checkUserInput()
            else -> _uiState.update {
                it.copy(resetPasswordState = RequestState.IDLE)
            }
        }
    }


    private fun updateTextField(
        textFieldType: AuthField,
        value: String
    ) {
        viewModelScope.launch {
            if (textFieldType == AuthField.PASSWORD) {
                _uiState.update {
                    it.copy(password = value)
                }
            } else if (textFieldType == AuthField.CONFIRM_PASSWORD) {
                _uiState.update {
                    it.copy(newPassword = value)
                }
            }
        }
    }


    private fun checkUserInput() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(resetPasswordState = RequestState.LOADING)
            }

            authRepo.isPasswordMatch(
                password = uiState.value.password,
                newPassword = uiState.value.newPassword,
                onSuccess = {
                    resetPassword()
                },
                onError = {
                    _uiState.update {
                        it.copy(
                            resetPasswordState = RequestState.ERROR,
                            responseMessage = "Password not match"
                        )
                    }
                }
            )
        }
    }


    private fun resetPassword() {
        viewModelScope.launch {
            val response = authRepo.resetPassword(
                email = uiState.value.email,
                otp = uiState.value.otp,
                newPassword = uiState.value.newPassword
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.data?.message,
                        resetPasswordState = RequestState.SUCCESS
                    )
                }

            } else if (response is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.message.orEmpty(),
                        resetPasswordState = RequestState.ERROR
                    )
                }
            }
        }
    }


    private fun saveOtpAndEmail(
        email: String,
        otp: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    email = email,
                    otp = otp
                )
            }
        }
    }
}