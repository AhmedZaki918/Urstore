package com.example.urstore.presentation.password_reset.enter_code

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.EMAIL_ADDRESS
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.util.ActionLabel
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
class EnterCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepo: AuthRepo
) : BaseViewModel<EnterCodeIntent>() {

    private val _uiState = MutableStateFlow(EnterCodeUiState())
    val uiState: StateFlow<EnterCodeUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        savedStateHandle.get<String>(EMAIL_ADDRESS)?.let {
            saveEmailAddress(email = it)
        }
    }

    override fun onIntent(intent: EnterCodeIntent) {
        when (intent) {
            is EnterCodeIntent.UpdateOtpField -> {
                updateOtp(intent.index, intent.value)
                generateOtp()
            }
            is EnterCodeIntent.ResendCode -> sendCode()
        }
    }


    private fun updateOtp(
        index: Int,
        value: String
    ) {
        viewModelScope.launch {
            if (uiState.value.verifyCodeState == RequestState.ERROR){
                updateVerifyState(RequestState.IDLE)
            }

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


    private fun generateOtp() {
        viewModelScope.launch {
            var otp = ""
            uiState.value.otpSetup.apply {
                otp = firstDigit + secondDigit + thirdDigit +
                        fourthDigit + fifthDigit + sixthDigit
            }

            if (otp.length == 6) {
                verifyOtp(otp)
            }
        }
    }


    private fun verifyOtp(otp: String) {
        viewModelScope.launch {
            updateVerifyState(RequestState.LOADING)

            val response = authRepo.verifyOtp(
                email = uiState.value.email,
                otp = otp
            )

            if (response is Resource.Success) {
                _effects.emit(UiEffect.NavigateWithTwoArgs(uiState.value.email, otp))
                updateVerifyState(RequestState.SUCCESS)

            } else if (response is Resource.Failure) {
                updateVerifyState(RequestState.ERROR)
                clearOtpFields()
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
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
            updateResendState(RequestState.LOADING)
            val response = authRepo.forgetPassword(uiState.value.email)

            if (response is Resource.Success) {
                updateResendState(RequestState.SUCCESS)
                clearOtpFields()
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "OTP sent successfully.",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )

            } else if (response is Resource.Failure) {
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = response.message.orEmpty(),
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
                updateResendState(RequestState.IDLE)
            }
        }
    }

    private fun clearOtpFields(){
        _uiState.update {
            it.copy(otpSetup = OtpSetup())
        }
    }

    private fun updateResendState(requestState: RequestState) {
        _uiState.update {
            it.copy(resendCodeState = requestState)
        }
    }

    private fun updateVerifyState(requestState: RequestState) {
        _uiState.update {
            it.copy(verifyCodeState = requestState)
        }
    }
}
