package com.example.urstore.presentation.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.data.model.cart.get.CartDto
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Lighter_Brown
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.TINY_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.Very_Light_Beige
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.CartSharedViewModel
import com.example.urstore.util.CheckoutFeesItem
import com.example.urstore.util.EditTextAlertDialog
import com.example.urstore.util.PaymentMethods
import com.example.urstore.util.UiEffect
import com.example.urstore.util.currentDate
import com.example.urstore.util.currentTime
import com.example.urstore.util.timePlusAnHour

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    cartSharedVM: CartSharedViewModel,
    navController: NavHostController
) {
    val cart = cartSharedVM.cartItems.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value


    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            if (effect is UiEffect.PobBackStack) {
                navController.popBackStack()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Very_Light_Beige)
            .padding(top = EXTRA_LARGE_MARGIN)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(bottom = SMALL_MARGIN)
        ) {
            item {
                CheckoutHeader {
                    viewModel.onIntent(CheckoutIntent.GoBack)
                }

                OrderHeader(
                    itemsCount = cart.shoppingCartList.size,
                    onEditClicked = {
                        viewModel.onIntent(CheckoutIntent.EditCart)
                    }
                )
            }

            itemsIndexed(cart.shoppingCartList) { index, item ->
                ListItemCheckout(
                    currentItem = item,
                    isLastItem = cart.shoppingCartList.size - 1 == index
                )
            }

            item {
                OrderFooter(cart)
                DeliveryDetails(
                    onChangeClicked = {
                        viewModel.onIntent(CheckoutIntent.ShowDialog(true))
                    },
                    uiState = uiState
                )
                PaymentMethods(
                    uiState = uiState,
                    onPaymentClicked = { payment ->
                        viewModel.onIntent(CheckoutIntent.ChangePayment(payment))
                    }
                )
            }
        }

        EditTextAlertDialog(
            isVisible = uiState.isDialogActive,
            newInput = uiState.deliveryAddress,
            confirmTitle = stringResource(R.string.save),
            dismissTitle = stringResource(R.string.cancel),
            onValueChanged = { address ->
                viewModel.onIntent(CheckoutIntent.ChangeAddress(address))
            },
            onConfirm = {
                if (uiState.deliveryAddress.isNotEmpty()) {
                    viewModel.onIntent(CheckoutIntent.SaveAddress)
                    viewModel.onIntent(CheckoutIntent.ShowDialog(false))
                }
            },
            onDismiss = {
                viewModel.onIntent(CheckoutIntent.CancelAddress)
                viewModel.onIntent(CheckoutIntent.ShowDialog(false))
            }
        )

        PlaceOrder(
            cart.totalAmount,
            onPlaceOrderClicked = {
                navController.navigate(Screen.ORDER_DETAILS.route)
            }
        )
        CheckoutFooter()
    }
}


@Composable
fun CheckoutHeader(
    onBackClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, titleText, captionText) = createRefs()

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
                    top.linkTo(parent.top, VERY_SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = stringResource(R.string.checkout),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Text(
            modifier = Modifier
                .constrainAs(captionText) {
                    top.linkTo(titleText.bottom, SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = stringResource(R.string.caption_checkout),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}


@Composable
fun OrderHeader(
    itemsCount: Int,
    onEditClicked: () -> Unit
) {
    Spacer(modifier = Modifier.height(MEDIUM_MARGIN))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(
            topStart = MEDIUM_MARGIN,
            topEnd = MEDIUM_MARGIN
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TINY_MARGIN
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_MARGIN)
        ) {
            val (orderText, itemsCountText, editCartText, arrowIcon) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(orderText) {
                        top.linkTo(parent.top, SMALL_MARGIN)
                        start.linkTo(parent.start)
                    }
                    .padding(top = SMALL_MARGIN),
                text = stringResource(R.string.ur_order),
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .constrainAs(itemsCountText) {
                        top.linkTo(orderText.top)
                        bottom.linkTo(orderText.bottom)
                        start.linkTo(orderText.end, SMALL_MARGIN)
                    }
                    .padding(top = SMALL_MARGIN),
                text = "($itemsCount ${stringResource(R.string.items)})",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier
                    .constrainAs(editCartText) {
                        top.linkTo(orderText.top)
                        bottom.linkTo(orderText.bottom)
                        end.linkTo(parent.end, MEDIUM_MARGIN)
                    }
                    .padding(top = SMALL_MARGIN)
                    .clickable {
                        onEditClicked()
                    },
                text = stringResource(R.string.edit_cart),
                color = Brown,
                fontSize = 12.sp
            )

            Icon(
                modifier = Modifier
                    .constrainAs(arrowIcon) {
                        start.linkTo(editCartText.end)
                        top.linkTo(editCartText.top)
                        bottom.linkTo(editCartText.bottom)
                    }
                    .size(22.dp)
                    .padding(top = SMALL_MARGIN),
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "",
                tint = Brown.copy(alpha = 0.8f)
            )
        }
    }
}


@Composable
fun OrderFooter(cart: CartDto) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = MEDIUM_MARGIN, end = MEDIUM_MARGIN
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(
            bottomStart = MEDIUM_MARGIN,
            bottomEnd = MEDIUM_MARGIN
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TINY_MARGIN
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    top = MEDIUM_MARGIN,
                    bottom = MEDIUM_MARGIN
                )
        ) {

            CheckoutFeesItem(
                stringResource(R.string.subtotal),
                "$${cart.totalAmount}",
                0.dp
            )

            CheckoutFeesItem(
                stringResource(R.string.delivery_fee),
                "$5.00",
                SMALL_MARGIN
            )

            CheckoutFeesItem(
                stringResource(R.string.total),
                "$${(cart.totalAmount?.plus(5.00))}",
                SMALL_MARGIN,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun DeliveryDetails(
    onChangeClicked: () -> Unit,
    uiState: CheckoutUiState
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentSize()
            .padding(
                top = MEDIUM_MARGIN,
                start = MEDIUM_MARGIN,
                bottom = VERY_SMALL_MARGIN
            )
    ) {
        val (locationIcon, titleText) = createRefs()

        Icon(
            modifier = Modifier
                .constrainAs(locationIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .size(25.dp)
                .padding(end = SMALL_MARGIN),
            imageVector = Icons.Default.LocationOn,
            contentDescription = "",
            tint = Color.Black
        )

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(locationIcon.top)
                    bottom.linkTo(locationIcon.bottom)
                    start.linkTo(locationIcon.end)
                }
                .wrapContentSize(),
            text = stringResource(R.string.delivery_details),
            fontWeight = FontWeight.Bold
        )
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MEDIUM_MARGIN),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TINY_MARGIN
        )
    ) {


        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    horizontal = LARGE_MARGIN,
                    vertical = SMALL_MARGIN
                )
        ) {
            val (addressColumn, lineDivider, timeColumn) = createRefs()


            VerticalDivider(
                modifier = Modifier
                    .constrainAs(lineDivider) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(vertical = SMALL_MARGIN),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.1f)
            )



            Column(
                modifier = Modifier
                    .constrainAs(addressColumn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .wrapContentSize()
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(R.string.address),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = SMALL_MARGIN),
                    text = uiState.deliveryAddress,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = SMALL_MARGIN)
                        .clickable {
                            onChangeClicked()
                        },
                    text = stringResource(R.string.change),
                    color = Brown,
                    fontSize = 12.sp
                )
            }


            Column(
                modifier = Modifier
                    .constrainAs(timeColumn) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .wrapContentSize()
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = "Time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = SMALL_MARGIN),
                    text = "${currentTime()} - ${timePlusAnHour()}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = VERY_SMALL_MARGIN),
                    text = currentDate(),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
fun PaymentMethods(
    uiState: CheckoutUiState,
    onPaymentClicked: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentSize()
            .padding(
                top = MEDIUM_MARGIN,
                start = MEDIUM_MARGIN,
                bottom = VERY_SMALL_MARGIN
            )
    ) {
        val (creditCardIcon, titleText) = createRefs()

        Icon(
            modifier = Modifier
                .constrainAs(creditCardIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .size(25.dp)
                .padding(end = SMALL_MARGIN),
            imageVector = Icons.Default.CreditCard,
            contentDescription = "",
            tint = Color.Black
        )

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(creditCardIcon.top)
                    bottom.linkTo(creditCardIcon.bottom)
                    start.linkTo(creditCardIcon.end)
                }
                .wrapContentSize(),
            text = stringResource(R.string._2_payment_methods),
            fontWeight = FontWeight.Bold
        )
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN, vertical = VERY_SMALL_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MEDIUM_MARGIN),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TINY_MARGIN
        )
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (visaIcon, mastercardIcon, visaRadioBtn, mastercardRadioBtn, visaText,
                mastercardText, lineDivider, secondLineDivider, cashRadioBtn, cashIcon, cashText,
                defaultSurface
            ) = createRefs()


            Surface(
                modifier = Modifier.constrainAs(defaultSurface) {
                    end.linkTo(parent.end, MEDIUM_MARGIN)
                    top.linkTo(parent.top, SMALL_MARGIN)
                },
                shape = RoundedCornerShape(SMALL_MARGIN),
                color = Color(0xFFE8F5E9)
            ) {
                Text(
                    text = stringResource(R.string._default),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 11.sp
                )
            }


            RadioButton(
                modifier = Modifier
                    .constrainAs(visaRadioBtn) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(parent.top)
                    }
                    .size(40.dp),
                selected = uiState.paymentMethods == PaymentMethods.VISA.value,
                onClick = {
                    onPaymentClicked(PaymentMethods.VISA.value)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Brown,
                    unselectedColor = Color.Gray
                )
            )


            Image(
                modifier = Modifier
                    .constrainAs(visaIcon) {
                        start.linkTo(visaRadioBtn.end)
                        top.linkTo(visaRadioBtn.top)
                        bottom.linkTo(visaRadioBtn.bottom)
                    }
                    .height(50.dp)
                    .width(30.dp),
                painter = painterResource(R.drawable.visa),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.constrainAs(visaText) {
                    start.linkTo(visaIcon.end, MEDIUM_MARGIN)
                    top.linkTo(visaIcon.top)
                    bottom.linkTo(visaIcon.bottom)
                },
                text = "Visa",
                fontSize = 11.sp
            )


            HorizontalDivider(
                modifier = Modifier
                    .constrainAs(lineDivider) {
                        top.linkTo(visaRadioBtn.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = MEDIUM_MARGIN),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.1f)
            )


            RadioButton(
                modifier = Modifier
                    .constrainAs(mastercardRadioBtn) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(lineDivider.bottom)
                    }
                    .size(40.dp),
                selected = uiState.paymentMethods == PaymentMethods.MASTERCARD.value,
                onClick = {
                    onPaymentClicked(PaymentMethods.MASTERCARD.value)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Brown,
                    unselectedColor = Color.Gray
                )
            )


            Image(
                modifier = Modifier
                    .constrainAs(mastercardIcon) {
                        start.linkTo(mastercardRadioBtn.end)
                        top.linkTo(mastercardRadioBtn.top)
                        bottom.linkTo(mastercardRadioBtn.bottom)
                    }
                    .size(25.dp),
                painter = painterResource(R.drawable.mastercard),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.constrainAs(mastercardText) {
                    start.linkTo(mastercardIcon.end, MEDIUM_MARGIN)
                    top.linkTo(mastercardIcon.top)
                    bottom.linkTo(mastercardIcon.bottom)
                },
                text = "Mastercard",
                fontSize = 11.sp
            )


            HorizontalDivider(
                modifier = Modifier
                    .constrainAs(secondLineDivider) {
                        top.linkTo(mastercardRadioBtn.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = MEDIUM_MARGIN),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.1f)
            )


            RadioButton(
                modifier = Modifier
                    .constrainAs(cashRadioBtn) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(secondLineDivider.bottom)
                    }
                    .size(40.dp),
                selected = uiState.paymentMethods == PaymentMethods.CASH.value,
                onClick = {
                    onPaymentClicked(PaymentMethods.CASH.value)
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Brown,
                    unselectedColor = Color.Gray
                )
            )

            Icon(
                modifier = Modifier
                    .constrainAs(cashIcon) {
                        start.linkTo(cashRadioBtn.end)
                        top.linkTo(cashRadioBtn.top)
                        bottom.linkTo(cashRadioBtn.bottom)
                    }
                    .size(25.dp)
                    .padding(end = SMALL_MARGIN),
                imageVector = Icons.Default.Money,
                contentDescription = "",
                tint = Color.Black
            )


            Text(
                modifier = Modifier.constrainAs(cashText) {
                    start.linkTo(cashIcon.end, MEDIUM_MARGIN)
                    top.linkTo(cashIcon.top)
                    bottom.linkTo(cashIcon.bottom)
                },
                text = "Cash on delivery",
                fontSize = 11.sp
            )
        }
    }
}


@Composable
fun CheckoutFooter() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp)
            .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN, bottom = VERY_SMALL_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Lighter_Brown),
        shape = RoundedCornerShape(SMALL_MARGIN)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = SMALL_MARGIN),
            horizontalArrangement = Arrangement.Center
        ) {
            InfoItem(
                title = stringResource(R.string.secure_payment),
                caption = stringResource(R.string.your_payment_is_safe),
                icon = Icons.Outlined.Security
            )

            InfoItem(
                title = stringResource(R.string.fast_delivery),
                caption = stringResource(R.string.time_your_door),
                icon = Icons.Outlined.DeliveryDining
            )

            InfoItem(
                title = stringResource(R.string._24_7_support),
                caption = stringResource(R.string.here_to_help),
                icon = Icons.Outlined.SupportAgent
            )
        }
    }
}


@Composable
fun RowScope.InfoItem(
    title: String,
    caption: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .weight(1f)
    ) {
        Icon(
            modifier = Modifier
                .size(27.dp)
                .padding(end = SMALL_MARGIN),
            imageVector = icon,
            contentDescription = "",
            tint = Color.Black
        )

        Column(modifier = Modifier.wrapContentSize()) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = VERY_SMALL_MARGIN),
                text = title,
                color = Color.Black,
                fontSize = 9.sp
            )

            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = caption,
                color = Color.Gray,
                fontSize = 9.sp
            )
        }
    }
}


@Composable
fun PlaceOrder(
    totalAmount: Int?,
    onPlaceOrderClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp)
    ) {
        // Place Order Button
        Column(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f)
        ) {
            ButtonShopApp(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(top = SMALL_MARGIN, start = MEDIUM_MARGIN),
                onButtonClicked = {
                    onPlaceOrderClicked()
                },
                label = stringResource(R.string.place_order),
                textFontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(SMALL_MARGIN))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = LARGE_MARGIN)
            ) {

                Icon(
                    modifier = Modifier
                        .size(12.dp),
                    imageVector = Icons.Default.Lock,
                    contentDescription = "",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = SMALL_MARGIN),
                    color = Color.Gray,
                    text = stringResource(R.string.encrypted_payment),
                    fontSize = 10.sp
                )
            }
        }


        // Subtotal info
        Card(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .padding(
                    top = SMALL_MARGIN,
                    bottom = SMALL_MARGIN,
                    start = SMALL_MARGIN,
                    end = MEDIUM_MARGIN
                ),
            colors = CardDefaults.cardColors(containerColor = Lighter_Brown),
            shape = RoundedCornerShape(SMALL_MARGIN)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        horizontal = SMALL_MARGIN,
                        vertical = SMALL_MARGIN
                    )
            ) {
                val (titleColumn, valueColumn) = createRefs()

                Column(
                    modifier = Modifier
                        .constrainAs(titleColumn) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                        .wrapContentSize()
                ) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(R.string.subtotal),
                        color = Color.Gray,
                        fontSize = 11.sp
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = VERY_SMALL_MARGIN),
                        text = stringResource(R.string.delivery_fee),
                        color = Color.Gray,
                        fontSize = 11.sp
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = SMALL_MARGIN),
                        text = stringResource(R.string.total),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }


                Column(
                    modifier = Modifier
                        .constrainAs(valueColumn) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .wrapContentSize()
                ) {
                    Text(
                        modifier = Modifier.wrapContentSize(),
                        color = Color.Gray,
                        text = "$$totalAmount",
                        fontSize = 12.sp
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = VERY_SMALL_MARGIN),
                        text = "$5.00",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = VERY_SMALL_MARGIN),
                        text = "$${(totalAmount?.plus(5.00))}",
                        color = Brown,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}