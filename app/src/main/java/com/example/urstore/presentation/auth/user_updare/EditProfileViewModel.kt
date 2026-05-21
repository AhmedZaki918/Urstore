package com.example.urstore.presentation.auth.user_updare

import androidx.lifecycle.viewModelScope
import com.example.urstore.data.local.Constants.ADDRESS
import com.example.urstore.data.local.Constants.DISPLAY_NAME
import com.example.urstore.data.local.Constants.EMAIL
import com.example.urstore.data.local.Constants.PHONE
import com.example.urstore.data.local.Constants.TOKEN
import com.example.urstore.data.network.Resource
import com.example.urstore.data.repository.AuthRepo
import com.example.urstore.util.ActionLabel
import com.example.urstore.util.AuthField
import com.example.urstore.util.BaseViewModel
import com.example.urstore.util.DataStoreRepo
import com.example.urstore.util.RequestState
import com.example.urstore.util.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val dataStore: DataStoreRepo
) : BaseViewModel<EditProfileIntent>() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<UiEffect>()
    val effects = _effects.asSharedFlow()

    init {
        displayUserInfo()
    }

    override fun onIntent(intent: EditProfileIntent) {
        when (intent) {
            is EditProfileIntent.UpdateTextField -> {
                updateTextField(intent.textFieldType, intent.value)
            }

            is EditProfileIntent.SaveChanges -> {
                if (isInputValid()) saveUserInfo()
                else {
                    sendEffect(
                        UiEffect.ShowSnackbar(
                            message = "All inputs are required",
                            actionLabel = ActionLabel.ERROR.value
                        )
                    )
                }
            }

            is EditProfileIntent.GoBack -> sendEffect(UiEffect.PobBackStack)
            is EditProfileIntent.Cancel -> displayUserInfo()
        }
    }


    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effects.emit(effect)
        }
    }

    private fun isInputValid(): Boolean {
        return uiState.value.fullName.isNotEmpty() &&
                uiState.value.phoneNumber.isNotEmpty() &&
                uiState.value.address.isNotEmpty()
    }

    private fun saveUserInfo() {
        viewModelScope.launch {
            updateState(RequestState.LOADING)

            val response = authRepo.updateUser(
                token = dataStore.readString(TOKEN).first(),
                fullName = uiState.value.fullName,
                address = uiState.value.address,
                email = uiState.value.email,
                phoneNumber = uiState.value.phoneNumber
            )

            if (response is Resource.Success) {
                authRepo.saveUserByDataStore(
                    fullName = uiState.value.fullName,
                    phone = uiState.value.phoneNumber,
                    address = uiState.value.address,
                )
                updateState(RequestState.SUCCESS)
                sendEffect(
                    UiEffect.ShowSnackbar(
                        message = "User updated successfully",
                        actionLabel = ActionLabel.SUCCESS.value
                    )
                )

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


    private fun displayUserInfo() {
        viewModelScope.launch {
            combine(
                dataStore.readString(EMAIL),
                dataStore.readString(DISPLAY_NAME),
                dataStore.readString(PHONE),
                dataStore.readString(ADDRESS)
            ) { email, fullName, phone, address ->

                _uiState.update {
                    it.copy(
                        fullName = fullName,
                        email = email,
                        phoneNumber = phone,
                        address = address
                    )
                }
            }.collect()
        }
    }


    private fun updateTextField(
        textFieldType: AuthField,
        value: String
    ) {
        viewModelScope.launch {
            when (textFieldType) {
                AuthField.NAME ->
                    _uiState.update {
                        it.copy(fullName = value)
                    }

                AuthField.ADDRESS ->
                    _uiState.update {
                        it.copy(address = value)
                    }

                AuthField.PHONE ->
                    _uiState.update {
                        it.copy(phoneNumber = value)
                    }

                else -> Unit
            }
        }
    }


    private fun updateState(state: RequestState) {
        _uiState.update {
            it.copy(profileState = state)
        }
    }
}