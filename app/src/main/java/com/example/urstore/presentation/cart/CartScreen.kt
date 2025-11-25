package com.example.urstore.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.Very_Light_Beige
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.cartDummy


@Composable
fun CartScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Very_Light_Beige)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.60f)
                .fillMaxWidth()
                .padding(top = EXTRA_LARGE_MARGIN, bottom = LARGE_MARGIN)

        ) {
            item {
                CartHeader(
                    onBackClicked = {
                        navController.popBackStack()
                    })
            }

            items(cartDummy()) {  item ->
                ListItemCart(currentItem = item)
            }
        }

        CheckoutSection()
    }
}


@Composable
fun CartHeader(
    onBackClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = MEDIUM_MARGIN)
    ) {
        val (backButton, titleText) = createRefs()

        BackButton(
            modifier = Modifier
                .constrainAs(backButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = MEDIUM_MARGIN),
            onBackClicked = {
                onBackClicked()
            }
        )


        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Cart",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}


@Composable
fun CheckoutSection() {


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (discountRow, checkoutColumn) = createRefs()


        Row(
            modifier = Modifier
                .constrainAs(discountRow){
                    bottom.linkTo(checkoutColumn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    horizontal = MEDIUM_MARGIN
                )
                .clip(RoundedCornerShape(LARGE_MARGIN))
                .background(Beige),
            Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.padding(
                    start = MEDIUM_MARGIN,
                    top = MEDIUM_MARGIN,
                    bottom = MEDIUM_MARGIN
                ),
                text = "Enter Your Discount Code",
                fontSize = 12.sp
            )


            ButtonShopApp(
                modifier = Modifier.padding(end = VERY_SMALL_MARGIN),
                onButtonClicked = {},
                label = "Apply"
            )


        }




        Column(
            modifier = Modifier
                .constrainAs(checkoutColumn){
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = MEDIUM_MARGIN)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(Beige)
        ) {

            CheckoutItem(
                "Subtotal",
                "$17",
                EXTRA_LARGE_MARGIN
            )

            CheckoutItem(
                "Delivery",
                "$0.0",
                MEDIUM_MARGIN
            )

            CheckoutItem(
                "Total Tax",
                "$0.1",
                MEDIUM_MARGIN
            )


            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_MARGIN, vertical = MEDIUM_MARGIN),
                thickness = 1.dp,
                color = Black
            )


            CheckoutItem(
                "Total",
                "$17.1",
                0.dp
            )

            ButtonShopApp(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        top = CUSTOM_MARGIN,
                        start = MEDIUM_MARGIN,
                        end = MEDIUM_MARGIN
                    ),
                onButtonClicked = {},
                label = "Proceed to Checkout"
            )
        }

    }
}

@Composable
fun CheckoutItem(
    title: String,
    value: String,
    paddingTop: Dp
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = paddingTop),
        Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = CUSTOM_MARGIN),
            text = title,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(end = CUSTOM_MARGIN),
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}