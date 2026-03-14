package com.example.urstore.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.AuthField
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.LoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title
import com.example.urstore.util.toast

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current


    when (uiState.loginState) {
        RequestState.ERROR -> {
            context.toast(uiState.responseMessage)
            viewModel.onIntent(LoginIntent.ClearErrorState)
        }

        RequestState.SUCCESS -> {
            navController.navigate(Screen.HOME_SCREEN.route)
        }

        else -> Unit
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.cup),
            contentDescription = "",
            modifier = Modifier.size(160.dp)
        )

        Title(
            id = R.string.welcome_back,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            fontSize = 35.sp
        )

        SubTitle(
            id = R.string.login_to_ur_account,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = SMALL_MARGIN),
            fontSize = 18.sp,
            color = Color.Black.copy(alpha = 0.6f)
        )

        TextFieldShopApp(
            input = uiState.email,
            onInputChange = { email ->
                viewModel.onIntent(
                    LoginIntent.UpdateTextField(AuthField.EMAIL, email)
                )
            },
            placeholder = "Email Address",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            topPadding = CUSTOM_MARGIN
        )

        TextFieldShopApp(
            input = uiState.password,
            onInputChange = { password ->
                viewModel.onIntent(
                    LoginIntent.UpdateTextField(AuthField.PASSWORD, password)
                )
            },
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password
        )


        Text(
            text = "Forgot password?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MEDIUM_MARGIN, end = CUSTOM_MARGIN),
            textAlign = TextAlign.End,
            fontSize = 14.sp
        )

        ButtonShopApp(
            isVisible = uiState.loginState != RequestState.LOADING,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = MEDIUM_MARGIN),
            label = "Login",
            onButtonClicked = {
                viewModel.onIntent(LoginIntent.Login)
            }
        )

        LoadingIndicator(
            modifier = Modifier.height(55.dp).wrapContentWidth(),
            isVisible = uiState.loginState == RequestState.LOADING)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screen.SIGNUP_SCREEN.route)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            SubTitle(
                id = R.string.dont_have_account,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = MEDIUM_MARGIN, end = VERY_SMALL_MARGIN),
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.6f)
            )

            SubTitle(
                id = R.string.signup,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = MEDIUM_MARGIN),
                fontSize = 16.sp,
                color = Brown
            )
        }
    }
}