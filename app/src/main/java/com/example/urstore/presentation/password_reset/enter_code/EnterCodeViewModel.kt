package com.example.urstore.presentation.password_reset.enter_code

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.urstore.R
import com.example.urstore.data.local.Constants.EMAIL_ADDRESS
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
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
class EnterCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepo: AuthRepo
) : BaseViewModel<EnterCodeIntent>() {

    private val _uiState = MutableStateFlow(EnterCodeUiState())
    val uiState: StateFlow<EnterCodeUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>(EMAIL_ADDRESS)?.let {
            saveEmailAddress(email = it)
        }
    }

    override fun onIntent(intent: EnterCodeIntent) {
        when (intent) {
            is EnterCodeIntent.UpdateOtpField -> updateOtp(intent.index, intent.value)
            is EnterCodeIntent.VerifyCode -> createOtp()
            is EnterCodeIntent.RevertStateToIdle ->
                _uiState.update {
                    it.copy(
                        verifyCodeState = RequestState.IDLE
                    )
                }

            is EnterCodeIntent.ResendCode -> sendCode()
        }
    }


    private fun updateOtp(
        index: Int,
        value: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                when (index) {
                    0 -> it.copy(otpSetup = it.otpSetup.copy(firstDigit = value))
                    1 -> it.copy(otpSetup = it.otpSetup.copy(secondDigit = value))
                    2 -> it.copy(otpSetup = it.otpSetup.copy(thirdDigit = value))
                    3 -> it.copy(otpSetup = it.otpSetup.copy(fourthDigit = value))
                    4 -> it.copy(otpSetup = it.otpSetup.copy(fifthDigit = value))
                    else -> it.copy(otpSetup = it.otpSetup.copy(sixthDigit = value))
                }
            }
        }
    }


    private fun createOtp() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(verifyCodeState = RequestState.LOADING)
            }

            var otp = ""
            uiState.value.otpSetup.apply {
                otp = firstDigit + secondDigit + thirdDigit +
                        fourthDigit + fifthDigit + sixthDigit
            }

            if (otp.length == 6) {
                _uiState.update { it.copy(otp = otp) }
                verifyOtp(otp)
            } else {
                _uiState.update {
                    it.copy(
                        verifyCodeState = RequestState.ERROR,
                        responseMessage = "OTP must be 6 digits."
                    )
                }
            }
        }
    }


    private fun verifyOtp(otp: String) {
        viewModelScope.launch {
            val response = authRepo.verifyOtp(
                email = uiState.value.email,
                otp = otp
            )

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.data?.message,
                        verifyCodeState = RequestState.SUCCESS
                    )
                }

            } else if (response is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.message.orEmpty(),
                        verifyCodeState = RequestState.ERROR
                    )
                }
            }
        }
    }

    private fun saveEmailAddress(email: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(email = email)
            }
        }
    }

    private fun sendCode() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    resendCodeState = RequestState.LOADING
                )
            }
            val response = authRepo.forgetPassword(uiState.value.email)

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.data?.message,
                        resendCodeState = RequestState.SUCCESS
                    )
                }


            } else if (response is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.message.orEmpty(),
                        resendCodeState = RequestState.ERROR
                    )
                }
            }
        }
    }
}
