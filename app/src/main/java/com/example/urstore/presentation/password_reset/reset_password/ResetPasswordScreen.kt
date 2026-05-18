package com.example.urstore.presentation.password_reset.reset_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.AuthField
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.SnackBar
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title
import com.example.urstore.util.UiEffect


@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    navController: NavHostController
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

                is UiEffect.Navigate -> navController.navigate(effect.route)
                is UiEffect.PobBackStack -> navController.popBackStack()
                else -> Unit
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 50.dp)
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            ResetPasswordUi( uiState, viewModel)
        }
        SnackBar(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        )
    }
}


@Composable
fun ResetPasswordUi(
    uiState: ResetPasswordUiState,
    viewModel: ResetPasswordViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, logoImage, resetPasswordText, captionText,
            passwordTextField, confirmPasswordTextField, resetPasswordButton, loadingBox) = createRefs()

        BackButton(
            modifier = Modifier.constrainAs(backButton) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, MEDIUM_MARGIN)
            },
            onBackClicked = {
                viewModel.onIntent(ResetPasswordIntent.GoBack)
            },
            isBackTextVisible = false
        )

        Image(
            modifier = Modifier
                .constrainAs(logoImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(160.dp),
            painter = painterResource(id = R.drawable.cup),
            contentDescription = stringResource(R.string.company_logo)
        )

        Title(
            modifier = Modifier
                .constrainAs(resetPasswordText) {
                    top.linkTo(logoImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            id = R.string.reset_password,
            fontSize = 30.sp
        )

        SubTitle(
            modifier = Modifier
                .constrainAs(captionText) {
                    top.linkTo(resetPasswordText.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.7f),
            id = R.string.create_new_password,
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 16.sp,
            lineHeight = 20.sp
        )


        TextFieldShopApp(
            modifier = Modifier.constrainAs(passwordTextField) {
                top.linkTo(captionText.bottom, MEDIUM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            input = uiState.password,
            onInputChange = { password ->
                viewModel.onIntent(
                    ResetPasswordIntent.UpdateTextField(
                        AuthField.PASSWORD, password
                    )
                )
            },
            placeholder = stringResource(R.string.new_password),
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password
        )


        TextFieldShopApp(
            modifier = Modifier.constrainAs(confirmPasswordTextField) {
                top.linkTo(passwordTextField.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            input = uiState.newPassword,
            onInputChange = { confirmPassword ->
                viewModel.onIntent(
                    ResetPasswordIntent.UpdateTextField(
                        AuthField.CONFIRM_PASSWORD, confirmPassword
                    )
                )
            },
            placeholder = stringResource(R.string.confirm_password),
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password
        )


        ButtonShopApp(
            isVisible = uiState.resetPasswordState != RequestState.LOADING,
            modifier = Modifier
                .constrainAs(resetPasswordButton) {
                    top.linkTo(confirmPasswordTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(
                    start = CUSTOM_MARGIN,
                    end = CUSTOM_MARGIN,
                    top = 12.dp
                ),
            label = "Reset Password",
            onButtonClicked = {
                viewModel.onIntent(
                    ResetPasswordIntent.ResetPassword
                )
            }
        )

        LinearLoadingIndicator(
            isVisible = uiState.resetPasswordState == RequestState.LOADING,
            modifier = Modifier
                .constrainAs(loadingBox) {
                    top.linkTo(confirmPasswordTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(55.dp)
                .wrapContentWidth(),
        )
    }
}