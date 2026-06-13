package com.example.urstore.presentation.auth.user_updare

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.AuthField
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.CircleWithIcon
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.SnackBar
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title
import com.example.urstore.util.UiEffect
import com.example.urstore.util.UnderlineText

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is UiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = effect.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEffect.PobBackStack -> navController.popBackStack()
                else -> Unit
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 24.dp),
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BackButton(
                modifier = Modifier
                    .padding(start = MEDIUM_MARGIN, top = LARGE_MARGIN)
                    .align(Alignment.Start),
                isBackTextVisible = false,
                onBackClicked = {
                    viewModel.onIntent(EditProfileIntent.GoBack)
                }
            )


            Title(
                id = R.string.edit_profile,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = CUSTOM_MARGIN),
                fontSize = 30.sp
            )

            SubTitle(
                id = R.string.update_user_caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_MARGIN),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(MEDIUM_MARGIN))

            CircleWithIcon(
                modifier =  Modifier
                    .padding(end = SMALL_MARGIN)
                    .wrapContentSize()
                    .background(color = Beige.copy(alpha = 0.5f), shape = CircleShape),
                icon = Icons.Outlined.Person,
                iconTint = Brown,
                iconSize = 60.dp,
            )


            TextFieldShopApp(
                input = uiState.email,
                onInputChange = {},
                placeholder = "",
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Text,
                topPadding = CUSTOM_MARGIN,
                label = {
                    Text(
                        text = "Email Address",
                        color = Color.Gray,
                    )
                },
                readOnly = true
            )


            TextFieldShopApp(
                input = uiState.fullName,
                onInputChange = { name ->
                    viewModel.onIntent(
                        EditProfileIntent.UpdateTextField(AuthField.NAME, name)
                    )
                },
                placeholder = "",
                leadingIcon = Icons.Outlined.Person,
                keyboardType = KeyboardType.Text,
                label = {
                    Text(
                        text = "Full Name",
                        color = Color.Gray,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null
                    )
                }
            )


            TextFieldShopApp(
                input = uiState.phoneNumber,
                onInputChange = { phone ->
                    viewModel.onIntent(
                        EditProfileIntent.UpdateTextField(AuthField.PHONE, phone)
                    )
                },
                placeholder = "",
                leadingIcon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone,
                label = {
                    Text(
                        text = "Phone Number",
                        color = Color.Gray,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null
                    )
                }
            )


            TextFieldShopApp(
                input = uiState.address,
                onInputChange = { address ->
                    viewModel.onIntent(
                        EditProfileIntent.UpdateTextField(AuthField.ADDRESS, address)
                    )
                },
                placeholder = "",
                leadingIcon = Icons.Outlined.LocationOn,
                keyboardType = KeyboardType.Text,
                label = {
                    Text(
                        text = "Address",
                        color = Color.Gray,
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null
                    )
                }
            )



            ButtonShopApp(
                isVisible = uiState.profileState != RequestState.LOADING,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = CUSTOM_MARGIN),
                label = stringResource(R.string.save_changes),
                onButtonClicked = {
                    viewModel.onIntent(EditProfileIntent.SaveChanges)
                }
            )

            LinearLoadingIndicator(
                modifier = Modifier
                    .height(55.dp)
                    .wrapContentWidth(),
                isVisible = uiState.profileState == RequestState.LOADING
            )


            UnderlineText(
                id = R.string.cancel,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = VERY_SMALL_MARGIN)
                    .clickable {
                        viewModel.onIntent(EditProfileIntent.Cancel)
                    },
                fontSize = 16.sp,
                color = Brown
            )
        }

        SnackBar(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        )
    }
}