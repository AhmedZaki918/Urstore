package com.example.urstore.presentation.order.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.urstore.R
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Cacy
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.Green
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Lighter_Brown
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.TINY_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.Very_Light_Beige
import com.example.urstore.ui.theme.White
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.CheckoutFeesItem
import com.example.urstore.util.CircleWithIcon
import com.example.urstore.util.DeliveryTimeline
import com.example.urstore.util.currentDate
import com.example.urstore.util.currentTime
import com.example.urstore.util.timePlusAnHour

@Composable
fun OrderDetails() {

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
                OrderDetailsHeader {}
                OrderConfirmed()
                OrderTimeLine(DeliveryTimeline.PREPARING.value)
                DeliveryInfo()
                OrderItemsHeader(2)
            }

            items(2) {
                ListItemOrder()
            }

            item {
                OrderItemsFooter()
                PaymentMethod()
            }
        }

        NeedHelp()
    }
}


@Composable
fun OrderTimeLine(timelineState: String) {
    Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = VERY_SMALL_MARGIN
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Estimated Delivery info
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (timeIcon, estimatedText, dateText, timeText, orderStatusText) = createRefs()

                CircleWithIcon(
                    modifier = Modifier
                        .constrainAs(timeIcon) {
                            start.linkTo(parent.start, MEDIUM_MARGIN)
                            top.linkTo(parent.top, MEDIUM_MARGIN)
                        }
                        .wrapContentSize()
                        .background(color = Lighter_Brown, shape = CircleShape),
                    icon = Icons.Default.AccessAlarm,
                    iconTint = Brown
                )


                Text(
                    modifier = Modifier.constrainAs(estimatedText) {
                        top.linkTo(parent.top, MEDIUM_MARGIN)
                        start.linkTo(timeIcon.end, SMALL_MARGIN)
                    },
                    text = stringResource(R.string.estimated_delivery),
                    fontSize = 12.sp
                )

                Text(
                    modifier = Modifier.constrainAs(dateText) {
                        top.linkTo(estimatedText.bottom, 2.dp)
                        start.linkTo(estimatedText.start)
                    },
                    text = "Today, ${currentDate()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )


                Text(
                    modifier = Modifier.constrainAs(timeText) {
                        top.linkTo(dateText.bottom, 2.dp)
                        start.linkTo(estimatedText.start)
                    },
                    text = "${currentTime()} - ${timePlusAnHour()}",
                    color = Color.Gray,
                    fontSize = 11.sp
                )


                Surface(
                    modifier = Modifier.constrainAs(orderStatusText) {
                        end.linkTo(parent.end, MEDIUM_MARGIN)
                        top.linkTo(parent.top, CUSTOM_MARGIN)
                    },
                    shape = RoundedCornerShape(SMALL_MARGIN),
                    color = Cacy
                ) {
                    Text(
                        text = when (timelineState) {
                            DeliveryTimeline.PREPARING.value -> stringResource(R.string.preparing_order)
                            DeliveryTimeline.ON_THE_WAY.value -> stringResource(R.string.on_the_way)
                            else -> stringResource(R.string.delivered)
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(MEDIUM_MARGIN))


            // Timeline icons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_MARGIN)
            ) {
                Icon(
                    background = Brown,
                    icon = Icons.Outlined.ShoppingBag,
                    iconTint = White
                )
                Line(Brown)

                Icon(
                    background = Brown,
                    icon = Icons.Outlined.Coffee,
                    iconTint = White
                )




                Line(
                    if (timelineState == DeliveryTimeline.PREPARING.value) {
                        Color.LightGray.copy(alpha = 0.8f)
                    } else Brown
                )

                Icon(
                    background = if (timelineState == DeliveryTimeline.PREPARING.value) Color.LightGray.copy(
                        alpha = 0.6f
                    )
                    else Brown,
                    icon = Icons.Outlined.DeliveryDining,
                    iconTint = if (timelineState == DeliveryTimeline.PREPARING.value) Black.copy(
                        alpha = 0.5f
                    )
                    else White
                )


                Line(
                    if (timelineState == DeliveryTimeline.DELIVERED.value) Brown
                    else Color.LightGray.copy(alpha = 0.8f)
                )


                Icon(
                    background = if (timelineState == DeliveryTimeline.DELIVERED.value) Brown
                    else Color.LightGray.copy(alpha = 0.6f),
                    icon = Icons.Outlined.CheckCircle,
                    iconTint = if (timelineState == DeliveryTimeline.DELIVERED.value) White
                    else Black.copy(alpha = 0.5f)
                )
            }


            // Timeline captions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_MARGIN)
            ) {
                Caption(stringResource(R.string.confirmed))
                Caption(stringResource(R.string.preparing))
                Caption(stringResource(R.string.on_the_way))
                Caption(stringResource(R.string.delivered))
            }
            Spacer(modifier = Modifier.height(MEDIUM_MARGIN))
        }
    }
}


@Composable
fun DeliveryInfo(
    onChangeClicked: () -> Unit = {}
) {
    Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MEDIUM_MARGIN),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TINY_MARGIN
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN)
            ) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = SMALL_MARGIN),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = VERY_SMALL_MARGIN),
                    text = stringResource(R.string.delivery_details),
                    fontWeight = FontWeight.Bold
                )
            }



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
                        fontSize = 12.sp
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = VERY_SMALL_MARGIN),
                        text = "10th of Ramadan City",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

//                    Text(
//                        modifier = Modifier
//                            .wrapContentSize()
//                            .padding(top = VERY_SMALL_MARGIN)
//                            .clickable {
//                                onChangeClicked()
//                            },
//                        text = stringResource(R.string.change),
//                        color = Brown,
//                        fontSize = 12.sp
//                    )
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
}


@Composable
fun PaymentMethod() {
    Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(MEDIUM_MARGIN),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TINY_MARGIN
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN)
            ) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = SMALL_MARGIN),
                    imageVector = Icons.Default.CreditCard,
                    contentDescription = "",
                    tint = Color.Black
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = VERY_SMALL_MARGIN),
                    text = stringResource(R.string.payment_method),
                    fontWeight = FontWeight.Bold
                )
            }



            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        horizontal = MEDIUM_MARGIN
                    )
            ) {
                val (cashText, payStateSurface, amountText) = createRefs()


//                Icon(
//                    modifier = Modifier
//                        .constrainAs(cashIcon) {
//                            start.linkTo(parent.start)
//                            top.linkTo(parent.top)
//                        }
//                        .size(25.dp)
//                        .padding(end = SMALL_MARGIN),
//                    imageVector = Icons.Default.Money,
//                    contentDescription = "",
//                    tint = Color.Black
//                )


                Text(
                    modifier = Modifier.constrainAs(cashText) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(amountText.top)
                        bottom.linkTo(amountText.bottom)
                    },
                    text = "Cash on delivery",
                    fontSize = 11.sp
                )


                Surface(
                    modifier = Modifier.constrainAs(payStateSurface) {
                        end.linkTo(amountText.start, CUSTOM_MARGIN)
                        top.linkTo(amountText.top)
                        bottom.linkTo(amountText.bottom)
                    },
                    shape = RoundedCornerShape(SMALL_MARGIN),
                    color = Color(0xFFE8F5E9)
                ) {
                    Text(
                        text = stringResource(R.string.total),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.sp
                    )
                }


                Text(
                    modifier = Modifier
                        .constrainAs(amountText) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .padding(end = MEDIUM_MARGIN),
                    text = "$115.00",
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(MEDIUM_MARGIN))
        }
    }
}


@Composable
fun OrderConfirmed() {
    Spacer(modifier = Modifier.height(MEDIUM_MARGIN))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = VERY_SMALL_MARGIN
        )
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (confirmCircle, titleText, captionText, orderIdText, timeText) = createRefs()

            CircleWithIcon(
                modifier = Modifier
                    .constrainAs(confirmCircle) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(parent.top, MEDIUM_MARGIN)
                    }
                    .wrapContentSize()
                    .background(color = Green, shape = CircleShape),
                icon = Icons.Default.Done,
                iconTint = Color.White
            )

            Text(
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(confirmCircle.end, SMALL_MARGIN)
                    top.linkTo(confirmCircle.top)
                },
                text = stringResource(R.string.order_confirmed),
                fontSize = 16.sp
            )


            Text(
                modifier = Modifier
                    .constrainAs(captionText) {
                        start.linkTo(titleText.start)
                        top.linkTo(titleText.bottom)
                    }
                    .padding(bottom = 12.dp),
                text = stringResource(R.string.order_received),
                fontSize = 11.sp,
                color = Color.Gray
            )


            Text(
                modifier = Modifier.constrainAs(orderIdText) {
                    end.linkTo(parent.end, MEDIUM_MARGIN)
                    top.linkTo(titleText.top, VERY_SMALL_MARGIN)
                },
                text = "${stringResource(R.string.order_id)}123874 ",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )


            Text(
                modifier = Modifier.constrainAs(timeText) {
                    end.linkTo(orderIdText.end)
                    top.linkTo(orderIdText.bottom, TINY_MARGIN)
                },
                text = "${currentDate()} . ${currentTime()}",
                fontSize = 12.sp,
                color = Brown
            )
        }
    }
}


@Composable
fun OrderItemsFooter() {

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
                "$110.00",
                0.dp
            )

            CheckoutFeesItem(
                stringResource(R.string.delivery_fee),
                "$5.00",
                SMALL_MARGIN
            )

            CheckoutFeesItem(
                stringResource(R.string.total),
                "$115.00",
                SMALL_MARGIN,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OrderItemsHeader(itemsCount: Int) {
    Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))

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
            val (orderText, itemsCountText) = createRefs()

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
        }
    }
}


@Composable
fun OrderDetailsHeader(
    onBackClicked: () -> Unit = {}
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
            text = stringResource(R.string.order_details),
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
            text = stringResource(R.string.order_details_caption),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}


@Composable
fun RowScope.Caption(caption: String) {
    Text(
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center,
        text = caption,
        color = Color.Gray,
        fontSize = 11.sp
    )
}


@Composable
fun RowScope.Icon(
    background: Color,
    icon: ImageVector,
    iconTint: Color
) {
    CircleWithIcon(
        modifier = Modifier
            .weight(1f)
            .wrapContentSize()
            .background(
                color = background,
                shape = CircleShape
            ),
        icon = icon,
        iconTint = iconTint,
        iconSize = 35.dp,
        contentPadding = SMALL_MARGIN
    )
}

@Composable
fun RowScope.Line(color: Color) {
    HorizontalDivider(
        modifier = Modifier
            .weight(1f)
            .padding(top = MEDIUM_MARGIN),
        color = color
    )
}


@Composable
fun NeedHelp() {
    Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = VERY_SMALL_MARGIN
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (helpIcon, helpText, helpCaptionText, supportBtn) = createRefs()


            Icon(
                modifier = Modifier
                    .constrainAs(helpIcon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(27.dp)
                    .padding(start = MEDIUM_MARGIN, top = SMALL_MARGIN),
                imageVector = Icons.Outlined.SupportAgent,
                contentDescription = "",
                tint = Color.Black
            )

            Text(
                modifier = Modifier.constrainAs(helpText) {
                    top.linkTo(helpIcon.top,SMALL_MARGIN)
                    start.linkTo(helpIcon.end, SMALL_MARGIN)
                },
                text = stringResource(R.string.need_help),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.constrainAs(helpCaptionText) {
                    top.linkTo(helpText.bottom)
                    start.linkTo(helpText.start)
                },
                color = Color.Gray,
                text = stringResource(R.string.help_caption),
                fontSize = 11.sp
            )

            OutlinedButton(
                modifier = Modifier.constrainAs(supportBtn) {
                    end.linkTo(parent.end,MEDIUM_MARGIN)
                    top.linkTo(parent.top)
                }.defaultMinSize(
                        minWidth = 1.dp,
                        minHeight = 1.dp
                    ),
                onClick = { },
                border = BorderStroke(0.3.dp, Brown),
                shape = RoundedCornerShape(SMALL_MARGIN),
                contentPadding = PaddingValues(horizontal = SMALL_MARGIN, vertical = VERY_SMALL_MARGIN),
            ) {
                Text(
                    fontSize = 12.sp,
                    text = stringResource(R.string.contact_support),
                    color = Brown
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(SMALL_MARGIN))

    ButtonShopApp(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_MARGIN),
        label = stringResource(R.string.cancel_order),
        onButtonClicked = {},
        textFontSize = 14.sp,
        roundedCornerSize = SMALL_MARGIN
    )

    Spacer(modifier = Modifier.height(SMALL_MARGIN))

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.back_to_home),
        fontSize = 14.sp,
        color = Brown
    )
    Spacer(modifier = Modifier.height(SMALL_MARGIN))
}




