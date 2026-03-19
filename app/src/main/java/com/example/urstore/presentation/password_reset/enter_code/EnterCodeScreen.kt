package com.example.urstore.presentation.password_reset.enter_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.Off_White
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.SubTitle
import com.example.urstore.util.Title

@Composable
fun EnterCodeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Light_Beige)
            .padding(
                top = 50.dp
            ),
    ) {
        EnterCodeUi(navController)
    }
}

@Composable
fun EnterCodeUi(navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, logoImage, enterCodeText, captionText, emailText,
            enterCodeBelowText, sixDigitRow, verifyButton, resendCodeText) = createRefs()

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
            title = "ahmdzaki004@gmail.com",
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
            for (x in 1..6) {
                CodeItem()
            }
        }


        ButtonShopApp(
            modifier = Modifier
                .constrainAs(verifyButton) {
                    top.linkTo(sixDigitRow.bottom, MEDIUM_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(
                    start = CUSTOM_MARGIN,
                    end = CUSTOM_MARGIN,
                    top = 12.dp
                ),
            label = "Verify",
            onButtonClicked = {
                navController.navigate(route = Screen.RESET_PASSWORD_SCREEN.route)
                //viewModel.onIntent(ForgetPasswordIntent.SendCode)
            }
        )

        SubTitle(
            modifier = Modifier
                .constrainAs(resendCodeText) {
                    top.linkTo(verifyButton.bottom, SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            title = "Resend code in 00:45",
            color = Color.Gray
        )
    }
}


@Composable
fun CodeItem() {
    var code by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier
            .size(45.dp)
            .border(
                width = 0.1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            ),
        value = code,
        onValueChange = { value ->
            if (value.length <= 1) {
                code = value
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