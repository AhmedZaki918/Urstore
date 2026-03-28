package com.example.urstore.presentation.password_reset.forget_password

import androidx.lifecycle.viewModelScope
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
class ForgetPasswordViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : BaseViewModel<ForgetPasswordIntent>() {

    private val _uiState = MutableStateFlow(ForgetPasswordUiState())
    val uiState: StateFlow<ForgetPasswordUiState> = _uiState.asStateFlow()


    override fun onIntent(intent: ForgetPasswordIntent) {
        when (intent) {
            is ForgetPasswordIntent.ResetUiStateToIdle ->
                _uiState.update {
                    it.copy(forgetPasswordState = RequestState.IDLE)
                }

            is ForgetPasswordIntent.SendCode -> checkUserInput()
            is ForgetPasswordIntent.UpdateTextField ->
                updateTextField(
                    intent.textFieldType,
                    intent.value
                )
        }
    }


    private fun updateTextField(
        textFieldType: AuthField,
        value: String
    ) {
        viewModelScope.launch {
            if (textFieldType == AuthField.EMAIL) {
                _uiState.update {
                    it.copy(email = value)
                }
            }
        }
    }


    private fun checkUserInput() {
        viewModelScope.launch {
            if (uiState.value.email.isEmpty()) {
                _uiState.update {
                    it.copy(
                        responseMessage = "Email is required.",
                        forgetPasswordState = RequestState.ERROR
                    )
                }
            } else {
                sendCode()
            }
        }
    }

    private fun sendCode() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    forgetPasswordState = RequestState.LOADING
                )
            }
            val response = authRepo.forgetPassword(uiState.value.email)

            if (response is Resource.Success) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.data?.message,
                        forgetPasswordState = RequestState.SUCCESS
                    )
                }


            } else if (response is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        responseMessage = response.message.orEmpty(),
                        forgetPasswordState = RequestState.ERROR
                    )
                }
            }
        }
    }
}