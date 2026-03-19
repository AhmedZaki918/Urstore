package com.example.urstore.presentation.password_reset.reset_password

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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
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
import com.example.urstore.presentation.auth.signup.SignupIntent
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.AuthField
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title


@Composable
fun ResetPassword(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(
                top = 50.dp
            ),
    ) {
        ResetPasswordUi(navController)
    }
}


@Composable
fun ResetPasswordUi(navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, logoImage, resetPasswordText, captionText,
            passwordTextField, confirmPasswordTextField, resetPasswordButton) = createRefs()

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
                top.linkTo(captionText.bottom,MEDIUM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            input = "",
            onInputChange = { password ->
//                viewModel.onIntent(
//                    SignupIntent.UpdateTextField(AuthField.PASSWORD, password)
//                )
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
            input = "",
            onInputChange = { confirmPassword ->
//                viewModel.onIntent(
//                    SignupIntent.UpdateTextField(AuthField.CONFIRM_PASSWORD, confirmPassword)
//                )
            },
            placeholder = stringResource(R.string.confirm_password),
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password
        )


        ButtonShopApp(
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
                navController.navigate(Screen.LOGIN_SCREEN.route)
                //viewModel.onIntent(ForgetPasswordIntent.SendCode)
            }
        )
    }
}