package com.example.urstore.presentation.password_reset.enter_code

import androidx.lifecycle.viewModelScope
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
    private val authRepo: AuthRepo
) : BaseViewModel<EnterCodeIntent>() {

    private val _uiState = MutableStateFlow(EnterCodeUiState())
    val uiState: StateFlow<EnterCodeUiState> = _uiState.asStateFlow()


    override fun onIntent(intent: EnterCodeIntent) {
        when (intent) {
            is EnterCodeIntent.UpdateTextField -> updateTextField(
                index = intent.index,
                value = intent.value
            )

            is EnterCodeIntent.VerifyCode -> updateOtp()
            is EnterCodeIntent.RevertStateToIdle -> updateState(RequestState.IDLE)
        }
    }


    private fun updateTextField(
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


    private fun updateOtp() {
        viewModelScope.launch {
            var otp = ""
            uiState.value.otpSetup.apply {
                otp = firstDigit + secondDigit + thirdDigit + fourthDigit + fifthDigit + sixthDigit
            }
            _uiState.update {
                it.copy(otp = otp)
            }
            updateState(RequestState.SUCCESS)
        }
    }

    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(enterCodeState = state)
        }
    }
}
