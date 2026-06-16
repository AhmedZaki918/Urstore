package com.example.urstore.presentation.order.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.data.model.orders.OrderState
import com.example.urstore.presentation.details.DetailsIntent
import com.example.urstore.presentation.details.DetailsViewModel
import com.example.urstore.presentation.home.HomeViewModel
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Cacy
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.Very_Light_Beige

@Composable
fun OrderScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val uiState = viewModel.uiState.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Very_Light_Beige)
            .padding(top = LARGE_MARGIN)
    ) {
        OrdersHeader()
        OrdersStatusBar(
            orderState = uiState.orderState,
            onSortClicked = { id ->
                viewModel.onIntent(
                    OrdersIntent.OnSortClicked(id)
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {


        }
    }
}


@Composable
fun OrdersHeader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (titleText, captionText) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top, VERY_SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = stringResource(R.string.all_orders),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Text(
            modifier = Modifier
                .constrainAs(captionText) {
                    top.linkTo(titleText.bottom, VERY_SMALL_MARGIN)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = stringResource(R.string.track_orders),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun OrdersStatusBar(
    orderState: List<OrderState> = emptyList(),
    onSortClicked: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = CUSTOM_MARGIN, vertical = MEDIUM_MARGIN)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(MEDIUM_MARGIN)
            )
            .border(
                width = 0.2.dp,
                color = Color.LightGray.copy(alpha = 0.6f),
                shape = RoundedCornerShape(MEDIUM_MARGIN)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        for (item in orderState.indices) {
            StatusItemUi(
                modifier = Modifier.weight(1f),
                currentItem = orderState[item],
                onItemClicked = {
                    onSortClicked(orderState[item].id)
                }
            )
        }
    }
}


@Composable
fun StatusItemUi(
    modifier: Modifier,
    currentItem: OrderState,
    onItemClicked: () -> Unit
) {
    if (currentItem.isPressed) {
        Text(
            modifier = modifier

                .background(
                    color = Cacy,
                    shape = RoundedCornerShape(MEDIUM_MARGIN)
                )
                .padding(vertical = SMALL_MARGIN)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onItemClicked()
                },
            text = currentItem.title,
            color = Brown,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    } else {
        Text(
            modifier = modifier
                .padding(vertical = SMALL_MARGIN)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onItemClicked()
                },
            text = currentItem.title,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

