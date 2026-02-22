package com.example.urstore.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.SubTitle
import com.example.urstore.util.TextFieldShopApp
import com.example.urstore.util.Title

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


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
            input = email,
            onInputChange = {
                email = it
            },
            placeholder = "Email Address",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            topPadding = CUSTOM_MARGIN
        )

        TextFieldShopApp(
            input = password,
            onInputChange = {
                password = it
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = MEDIUM_MARGIN),
            label = "Login",
            onButtonClicked = {
            }
        )

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