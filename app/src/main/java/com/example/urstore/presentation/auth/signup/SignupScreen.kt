package com.example.urstore.presentation.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title
import com.example.urstore.util.UnderlineText


@Composable
fun SignupScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var fullName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }


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
            modifier = Modifier.fillMaxWidth().padding(top = LARGE_MARGIN),
            fontSize = 30.sp
        )


        TextFieldShopApp(
            input = fullName,
            onInputChange = {
                fullName = it
            },
            placeholder = "Full Name",
            leadingIcon = Icons.Outlined.Person,
            keyboardType = KeyboardType.Text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = CUSTOM_MARGIN)
                .shadow(2.dp, RoundedCornerShape(16.dp))
        )

        TextFieldShopApp(
            input = email,
            onInputChange = {
                email = it
            },
            placeholder = "Email Address",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = SMALL_MARGIN)
                .shadow(2.dp, RoundedCornerShape(16.dp))
        )

        TextFieldShopApp(
            input = password,
            onInputChange = {
                password = it
            },
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = SMALL_MARGIN)
                .shadow(2.dp, RoundedCornerShape(16.dp))
        )


        TextFieldShopApp(
            input = confirmPassword,
            onInputChange = {
                confirmPassword = it
            },
            placeholder = "Confirm Password",
            leadingIcon = Icons.Outlined.Lock,
            trailingIcon = Icons.Outlined.RemoveRedEye,
            keyboardType = KeyboardType.Password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = SMALL_MARGIN)
                .shadow(2.dp, RoundedCornerShape(16.dp))
        )


        ButtonShopApp(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = CUSTOM_MARGIN),
            label = "Sign Up",
            onButtonClicked = {

            }
        )


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
                .clickable{
                    navController.navigate(Screen.LOGIN_SCREEN.route)
                },
            fontSize = 16.sp,
            color = Brown
        )
    }
}