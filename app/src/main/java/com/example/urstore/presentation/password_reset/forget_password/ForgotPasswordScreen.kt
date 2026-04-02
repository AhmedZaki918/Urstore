package com.example.urstore.presentation.password_reset.forget_password

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
import androidx.compose.material.icons.outlined.Email
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
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.AuthField
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.LoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.SnackBar
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title
import com.example.urstore.util.UiEffect

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgetPasswordViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is UiEffect.ShowSnackbar ->
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = effect.actionLabel,
                        duration = SnackbarDuration.Short
                    )

                is UiEffect.NavigateWithOneArg<*> ->
                    navController.navigate(
                        "${Screen.ENTER_CODE_SCREEN.route}/${effect.arg}"
                    )

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
            ForgotPasswordUi(navController, uiState, viewModel)
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
fun ForgotPasswordUi(
    navController: NavHostController,
    uiState: ForgetPasswordUiState,
    viewModel: ForgetPasswordViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, logoImage, forgotPasswordText,
            captionText, emailTextField, sendCodeButton, loadingView) = createRefs()

        BackButton(
            modifier = Modifier.constrainAs(backButton) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, MEDIUM_MARGIN)
            },
            onBackClicked = {
                navController.popBackStack()
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
                .constrainAs(forgotPasswordText) {
                    top.linkTo(logoImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            id = R.string.forgot_password,
            fontSize = 30.sp
        )

        SubTitle(
            modifier = Modifier
                .constrainAs(captionText) {
                    top.linkTo(forgotPasswordText.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.6f),
            id = R.string.forgot_password_caption,
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 16.sp,
            lineHeight = 20.sp
        )


        TextFieldShopApp(
            modifier = Modifier.constrainAs(emailTextField) {
                top.linkTo(captionText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            input = uiState.email,
            onInputChange = { email ->
                viewModel.onIntent(
                    ForgetPasswordIntent.UpdateTextField(
                        AuthField.EMAIL,
                        email
                    )
                )
            },
            placeholder = "Email Address",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            topPadding = CUSTOM_MARGIN
        )


        ButtonShopApp(
            isVisible = uiState.forgetPasswordState != RequestState.LOADING,
            modifier = Modifier
                .constrainAs(sendCodeButton) {
                    top.linkTo(emailTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(
                    start = CUSTOM_MARGIN,
                    end = CUSTOM_MARGIN,
                    top = 12.dp
                ),
            label = "Send Code",
            onButtonClicked = {
                viewModel.onIntent(ForgetPasswordIntent.SendCode)
            }
        )

        LoadingIndicator(
            isVisible = uiState.forgetPasswordState == RequestState.LOADING,
            modifier = Modifier
                .constrainAs(loadingView) {
                    top.linkTo(emailTextField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(55.dp)
                .wrapContentWidth(),
        )
    }
}