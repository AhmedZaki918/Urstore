package com.example.urstore.presentation.password_reset.enter_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.Off_White
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.LoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.SnackBar
import com.example.urstore.util.SubTitle
import com.example.urstore.util.Title
import com.example.urstore.util.UiEffect
import kotlinx.coroutines.delay

@Composable
fun EnterCodeScreen(
    viewModel: EnterCodeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequesters = List(6) { FocusRequester() }


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

                is UiEffect.NavigateWithTwoArgs<*, *> -> {
                    navController.navigate(
                        "${Screen.RESET_PASSWORD_SCREEN.route}/${effect.firstArg}/${effect.secondArg}"
                    )
                }

                else -> Unit
            }
        }
    }

    ClearFocusOnTextField(uiState.enterCodeState)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EnterCodeUi(navController, uiState, viewModel, focusRequesters)

        LoadingIndicator(
            isVisible = uiState.enterCodeState == RequestState.LOADING,
            modifier = Modifier
                .height(55.dp)
                .wrapContentWidth()
        )

        CountdownTimer(
            isVisible = uiState.enterCodeState != RequestState.LOADING,
            viewModel = viewModel
        )

        SnackBar(
            hostState = snackbarHostState,
            modifier = Modifier.padding(top = SMALL_MARGIN)
        )
    }
}


@Composable
fun EnterCodeUi(
    navController: NavHostController,
    uiState: EnterCodeUiState,
    viewModel: EnterCodeViewModel,
    focusRequesters: List<FocusRequester>
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, logoImage, enterCodeText, captionText, emailText,
            enterCodeBelowText, sixDigitRow) = createRefs()

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
                .constrainAs(enterCodeText) {
                    top.linkTo(logoImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            id = R.string.enter_code,
            fontSize = 30.sp
        )

        SubTitle(
            modifier = Modifier
                .constrainAs(captionText) {
                    top.linkTo(enterCodeText.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(horizontal = LARGE_MARGIN),
            id = R.string.six_digit_code,
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 16.sp,
            lineHeight = 20.sp
        )


        SubTitle(
            modifier = Modifier
                .constrainAs(emailText) {
                    top.linkTo(captionText.bottom, VERY_SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            title = uiState.email,
            fontWeight = FontWeight.SemiBold
        )


        SubTitle(
            modifier = Modifier
                .constrainAs(enterCodeBelowText) {
                    top.linkTo(captionText.bottom, EXTRA_LARGE_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            id = R.string.enter_code_below,
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 16.sp,
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_MARGIN)
                .constrainAs(sixDigitRow) {
                    top.linkTo(enterCodeBelowText.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterHorizontally
            )
        ) {
            for (otpIndex in 0..5) {
                Otp(otpIndex, uiState, viewModel, focusRequesters)
            }
        }
    }
}


@Composable
fun Otp(
    index: Int,
    uiState: EnterCodeUiState,
    viewModel: EnterCodeViewModel,
    focusRequesters: List<FocusRequester>
) {
    val focusRequester = focusRequesters[index]

    TextField(
        modifier = Modifier
            .size(46.dp)
            .border(
                width = if (uiState.enterCodeState == RequestState.ERROR) 0.5.dp else 0.1.dp,
                color = if (uiState.enterCodeState == RequestState.ERROR) Color.Red else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .focusRequester(focusRequester),
        value = returnDigitBasedOnOtpSetup(index, uiState.otpSetup),
        onValueChange = { value ->
            if (value.length <= 1) {
                viewModel.onIntent(
                    EnterCodeIntent.UpdateOtpField(
                        index = index,
                        value = value
                    )
                )

                // Move to next field automatically
                if (value.isNotEmpty() && index < focusRequesters.lastIndex) {
                    focusRequesters[index + 1].requestFocus()
                }
            }
        },
        placeholder = {
            Text(
                color = Color.Gray,
                text = "|"
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Off_White,
            unfocusedContainerColor = Off_White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}


@Composable
fun CountdownTimer(
    isVisible: Boolean,
    totalTime: Int = 45,
    viewModel: EnterCodeViewModel
) {
    if (isVisible) {
        var timeLeft by remember { mutableIntStateOf(totalTime) }
        LaunchedEffect(key1 = timeLeft) {
            if (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
        }

        SendCodeText(
            isVisible = timeLeft > 0,
            title = if (timeLeft >= 10) "${stringResource(R.string.send_code_00)}$timeLeft"
            else "${stringResource(R.string.send_code_00_0)}$timeLeft"
        )

        SendCodeText(
            isVisible = timeLeft == 0,
            title = stringResource(R.string.resend_code_now),
            textDecoration = TextDecoration.Underline,
            onClicked = {
                timeLeft = 45
                viewModel.onIntent(EnterCodeIntent.ResendCode)
            }
        )
    }
}


@Composable
fun SendCodeText(
    isVisible: Boolean,
    title: String,
    textDecoration: TextDecoration? = null,
    onClicked: () -> Unit = {}
) {
    if (isVisible) {
        SubTitle(
            modifier = Modifier
                .padding(top = MEDIUM_MARGIN)
                .wrapContentWidth()
                .clickable {
                    onClicked()
                },
            title = title,
            style = TextStyle(
                color = Color.Gray,
                textDecoration = textDecoration
            ),
        )
    }
}


fun returnDigitBasedOnOtpSetup(
    index: Int,
    otp: OtpSetup
): String {
    return when (index) {
        0 -> otp.firstDigit
        1 -> otp.secondDigit
        2 -> otp.thirdDigit
        3 -> otp.fourthDigit
        4 -> otp.fifthDigit
        else -> otp.sixthDigit
    }
}

@Composable
fun ClearFocusOnTextField(uiState: RequestState) {
    val focusManager = LocalFocusManager.current
    LaunchedEffect(uiState) {
        if (uiState == RequestState.LOADING) {
            focusManager.clearFocus()
        }
    }
}
