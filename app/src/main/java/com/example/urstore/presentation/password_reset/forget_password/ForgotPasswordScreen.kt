package com.example.urstore.presentation.password_reset.forget_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(
                top = 50.dp
            ),
    ) {
        ForgotPasswordUi(navController)
    }
}

@Composable
fun ForgotPasswordUi(navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, logoImage, forgotPasswordText,
            captionText, emailTextField, sendCodeButton) = createRefs()

        BackButton(
            modifier = Modifier.constrainAs(backButton) {
                top.linkTo(parent.top)
                start.linkTo(parent.start,MEDIUM_MARGIN)
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
            input = "",
            onInputChange = { email ->
//                viewModel.onIntent(
//                    ForgetPasswordIntent.UpdateTextField(AuthField.EMAIL, email)
//                )
            },
            placeholder = "Email Address",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            topPadding = CUSTOM_MARGIN
        )


        ButtonShopApp(
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
                navController.navigate(Screen.ENTER_CODE_SCREEN.route)
                //viewModel.onIntent(ForgetPasswordIntent.SendCode)
            }
        )
    }
}