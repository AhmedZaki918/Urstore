package com.example.urstore.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Dark_Beige
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.stashFont

@Composable
fun SplashScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (getStartedButton, titleText, imageBackground) = createRefs()


            Image(
                painter = painterResource(R.drawable.splash_pic),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(imageBackground) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxSize()
            )


            Text(
                textAlign = TextAlign.Center,
                lineHeight = 120.sp,
                text = "Coffee Shop",
                fontFamily = stashFont,
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold,
                color = Dark_Beige,
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, LARGE_MARGIN)
                }
            )


            Image(
                painter = painterResource(R.drawable.start_btn),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(getStartedButton) {
                        end.linkTo(parent.end, CUSTOM_MARGIN)
                        top.linkTo(titleText.bottom,CUSTOM_MARGIN)
                    }
                    .clickable {
                        navController.navigate(Screen.HOME_SCREEN.route)
                    }
            )
        }
    }
}