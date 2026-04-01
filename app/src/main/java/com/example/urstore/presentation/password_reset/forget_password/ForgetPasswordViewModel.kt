package com.example.urstore.presentation.password_reset.forget_password

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
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
class ForgetPasswordViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : BaseViewModel<ForgetPasswordIntent>() {

    private val _uiState = MutableStateFlow(ForgetPasswordUiState())
    val uiState: StateFlow<ForgetPasswordUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()


    override fun onIntent(intent: ForgetPasswordIntent) {
        when (intent) {
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
                _effects.emit(
                    UiEffect.ShowSnackbar(
                        message = "Email is required.",
                        actionLabel = ActionLabel.ERROR.value
                    )
                )
            } else {
                sendCode()
            }
        }
    }

    private fun sendCode() {
        viewModelScope.launch {
            updateState(RequestState.LOADING)
            val response = authRepo.forgetPassword(uiState.value.email)

            if (response is Resource.Success) {
                updateState(RequestState.SUCCESS)
                _effects.emit(UiEffect.NavigateWithOneArg(uiState.value.email))

            } else if (response is Resource.Failure) {
                updateState(RequestState.ERROR)
                _effects.emit(
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
            it.copy(
                forgetPasswordState = state
            )
        }
    }
}