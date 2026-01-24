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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
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


@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

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


            if (uiState.cartItems.isNotEmpty()) {
                items(uiState.cartItems) { item ->
                    ListItemCart(
                        currentItem = item,
                        onDeleteClicked = {
                            viewModel.onIntent(
                                CartIntent.RemoveItem(item)
                            )
                        },
                        onDecreaseClicked = {
                            viewModel.onIntent(
                                CartIntent.DecreaseQuantity(item.id)
                            )
                        },
                        onIncreaseClicked = {
                            viewModel.onIntent(
                                CartIntent.IncreaseQuantity(item.id)
                            )
                        }
                    )
                }
            }
        }


        if (uiState.cartItems.isNotEmpty()) {
            CheckoutSection(uiState.subtotal)
        }
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
            text = stringResource(R.string.cart),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}


@Composable
fun CheckoutSection(subtotal: Double) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (discountRow, checkoutColumn) = createRefs()

        // Discount code
        Row(
            modifier = Modifier
                .constrainAs(discountRow) {
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
                text = stringResource(R.string.discount_code),
                fontSize = 12.sp
            )


            ButtonShopApp(
                modifier = Modifier.padding(end = VERY_SMALL_MARGIN),
                onButtonClicked = {},
                label = stringResource(R.string.apply)
            )
        }


        // Checkout
        Column(
            modifier = Modifier
                .constrainAs(checkoutColumn) {
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
                stringResource(R.string.subtotal),
                "$$subtotal",
                EXTRA_LARGE_MARGIN
            )

            CheckoutItem(
                stringResource(R.string.delivery),
                "$0.0",
                MEDIUM_MARGIN
            )

            CheckoutItem(
                stringResource(R.string.total_tax),
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
                stringResource(R.string.total),
                "$${(subtotal + 0.1)}",
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
                label = stringResource(R.string.proceed_to_checkout)
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