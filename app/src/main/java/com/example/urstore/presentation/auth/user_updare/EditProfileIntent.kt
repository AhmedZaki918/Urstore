package com.example.urstore.presentation.auth.user_updare

import com.example.urstore.util.AuthField

sealed class EditProfileIntent {
    object SaveChanges : EditProfileIntent()
    object Cancel : EditProfileIntent()
    object GoBack : EditProfileIntent()
    data class UpdateTextField(
        var textFieldType: AuthField,
        var value: String
    ) : EditProfileIntent()
}