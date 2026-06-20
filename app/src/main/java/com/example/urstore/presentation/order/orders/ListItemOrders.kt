package com.example.urstore.presentation.order.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.urstore.R
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Cacy
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.CurrentOrder
import com.example.urstore.util.DeliveryTimeline

@Composable
fun ListItemOrders(currentItem: CurrentOrder) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = SMALL_MARGIN, start = MEDIUM_MARGIN, end = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = VERY_SMALL_MARGIN
        ),
        shape = RoundedCornerShape(MEDIUM_MARGIN)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = SMALL_MARGIN)
        ) {
            val (imagesColumn, orderIdColumn, itemsColumn, addressRow,
                deliveryInfoColumn, arrowIcon, amountText) = createRefs()


            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN)
                    .constrainAs(imagesColumn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }) {
                for (index in currentItem.orderItems.indices) {
                    if (index < 2 ){
                        Image(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(currentItem.orderItems[index].imageId),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))
                    }
                }
            }


            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = MEDIUM_MARGIN)
                    .constrainAs(orderIdColumn) {
                        start.linkTo(imagesColumn.end, MEDIUM_MARGIN)
                        top.linkTo(imagesColumn.top)
                    }) {

                Text(
                    text = "${stringResource(R.string.order_id)}${currentItem.orderId}",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(top = VERY_SMALL_MARGIN),
                    text = currentItem.dateAndTime,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }


            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = MEDIUM_MARGIN)
                    .constrainAs(itemsColumn) {
                        start.linkTo(imagesColumn.end, MEDIUM_MARGIN)
                        top.linkTo(orderIdColumn.bottom)
                    }) {

                for (index in currentItem.orderItems.indices){
                    if (index < 2){
                        Text(
                            text = currentItem.orderItems[index].title,
                            color = Color.DarkGray.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(VERY_SMALL_MARGIN))
                    }
                }


               if (currentItem.orderItems.size > 2){
                   Text(
                       modifier = Modifier.padding(top = VERY_SMALL_MARGIN),
                       text = "+${currentItem.orderItems.size-2} item",
                       color = Color.DarkGray.copy(alpha = 0.85f),
                       fontSize = 12.sp
                   )
               }
            }


            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(addressRow) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(imagesColumn.bottom, SMALL_MARGIN)
                    }) {

                Icon(
                    modifier = Modifier
                        .size(23.dp)
                        .padding(end = SMALL_MARGIN),
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "",
                    tint = Color.Black.copy(alpha = 0.7f)
                )

                Text(
                    modifier = Modifier.padding(top = VERY_SMALL_MARGIN),
                    text = currentItem.address,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }




            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(deliveryInfoColumn) {
                        end.linkTo(arrowIcon.start, MEDIUM_MARGIN)
                        top.linkTo(arrowIcon.top)
                        bottom.linkTo(arrowIcon.bottom)
                    }) {

                Surface(
                    shape = RoundedCornerShape(SMALL_MARGIN),
                    color = when (currentItem.statusCaption) {
                        DeliveryTimeline.PREPARING.value -> Cacy
                        DeliveryTimeline.ON_THE_WAY.value -> Cacy
                        DeliveryTimeline.DELIVERED.value -> Color(0xFFE8F5E9)
                        else -> Color.LightGray.copy(alpha = 0.5f)
                    }
                ) {
                    Text(
                        text = when (currentItem.statusCaption) {
                            DeliveryTimeline.PREPARING.value -> stringResource(R.string.preparing)
                            DeliveryTimeline.ON_THE_WAY.value -> stringResource(R.string.on_the_way)
                            DeliveryTimeline.DELIVERED.value -> stringResource(R.string.delivered)
                            else -> stringResource(R.string.cancelled)
                        },
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = VERY_SMALL_MARGIN
                        ),
                        fontSize = 11.sp
                    )
                }
            }


            Text(
                modifier = Modifier
                    .constrainAs(amountText) {
                        start.linkTo(deliveryInfoColumn.start)
                        end.linkTo(deliveryInfoColumn.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(bottom = SMALL_MARGIN),
                text = "$${currentItem.totalAmount}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )


            Icon(
                modifier = Modifier
                    .constrainAs(arrowIcon) {
                        end.linkTo(parent.end, SMALL_MARGIN)
                        top.linkTo(parent.top, CUSTOM_MARGIN)
                    }
                    .size(14.dp),
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "",
                tint = Color.Gray
            )
        }
    }
}