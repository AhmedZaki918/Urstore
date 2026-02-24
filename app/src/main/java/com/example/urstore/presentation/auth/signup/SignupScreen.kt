package com.example.urstore.presentation.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.LoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.AuthField
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title
import com.example.urstore.util.UnderlineText
import com.example.urstore.util.toast


@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current


    when (uiState.signupState) {
        RequestState.ERROR -> {
            context.toast(stringResource(R.string.invalid_input))
            viewModel.onIntent(SignupIntent.ClearErrorState)
        }

        RequestState.SUCCESS -> navController.navigate(Screen.LOGIN_SCREEN.route)
        else -> Unit
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackButton(
            modifier = Modifier
                .padding(start = MEDIUM_MARGIN, top = LARGE_MARGIN)
                .align(Alignment.Start),
            isBackTextVisible = false,
            onBackClicked = {
                navController.popBackStack()
            }
        )


        Title(
            id = R.string.create_account,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = LARGE_MARGIN),
            fontSize = 30.sp
        )


        TextFieldShopApp(
            input = uiState.fullName,
            onInputChange = { fullName ->
                viewModel.onIntent(
                    SignupIntent.UpdateTextField(AuthField.NAME, fullName)
                )
            },
            placeholder = stringResource(R.string.full_name),
            leadingIcon = Icons.Outlined.Person,
            keyboardType = KeyboardType.Text,
            topPadding = CUSTOM_MARGIN
        )

        TextFieldShopApp(
            input = uiState.email,
            onInputChange = { email ->
                viewModel.onIntent(
                    SignupIntent.UpdateTextField(AuthField.EMAIL, email)
                )
            },
            placeholder = stringResource(R.string.email_address),
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email
        )

        TextFieldShopApp(
            input = uiState.phoneNumber,
            onInputChange = { phoneNumber ->
                viewModel.onIntent(
                    SignupIntent.UpdateTextField(AuthField.PHONE, phoneNumber)
                )
            },
            placeholder = stringResource(R.string.phone_number),
            leadingIcon = Icons.Outlined.Phone,
            keyboardType = KeyboardType.Phone
        )

        TextFieldShopApp(
            input = uiState.address,
            onInputChange = { address ->
                viewModel.onIntent(
                    SignupIntent.UpdateTextField(AuthField.ADDRESS, address)
                )
            },
            placeholder = stringResource(R.string.address),
            leadingIcon = Icons.Outlined.LocationOn,
            keyboardType = KeyboardType.Text
        )

        TextFieldShopApp(
            input = uiState.password,
            onInputChange = { password ->
                viewModel.onIntent(
                    SignupIntent.UpdateTextField(AuthField.PASSWORD, password)
                )
            },
            placeholder = stringResource(R.string.password),
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password
        )


        TextFieldShopApp(
            input = uiState.confirmPassword,
            onInputChange = { confirmPassword ->
                viewModel.onIntent(
                    SignupIntent.UpdateTextField(AuthField.CONFIRM_PASSWORD, confirmPassword)
                )
            },
            placeholder = stringResource(R.string.confirm_password),
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password
        )



        ButtonShopApp(
            isVisible = uiState.signupState != RequestState.LOADING,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = CUSTOM_MARGIN),
            label = stringResource(R.string.sign_up),
            onButtonClicked = {
                viewModel.onIntent(
                    SignupIntent.Signup
                )
            }
        )

        LoadingIndicator(isVisible = uiState.signupState == RequestState.LOADING)


        SubTitle(
            id = R.string.already_have_account,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = MEDIUM_MARGIN, end = VERY_SMALL_MARGIN),
            fontSize = 16.sp,
            color = Color.Black.copy(alpha = 0.6f)
        )


        UnderlineText(
            id = R.string.login,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = VERY_SMALL_MARGIN)
                .clickable {
                    navController.navigate(Screen.LOGIN_SCREEN.route)
                },
            fontSize = 16.sp,
            color = Brown
        )
    }
}


//@Composable
//fun UpdateSignupUiState(
//    uiState: SignupUiState,
//    viewModel: SignupViewModel,
//    context: Context,
//    navController: NavHostController
//) {
//    when (uiState.signupState) {
//        RequestState.ERROR -> {
//            context.toast(stringResource(R.string.invalid_input))
//            viewModel.onIntent(SignupIntent.ClearErrorState)
//        }
//
//        RequestState.SUCCESS -> navController.navigate(Screen.LOGIN_SCREEN.route)
//        else -> {}
//    }
//}


//    UpdateSignupUiState(
//        uiState,
//        viewModel,
//        context,
//        navController
//    )