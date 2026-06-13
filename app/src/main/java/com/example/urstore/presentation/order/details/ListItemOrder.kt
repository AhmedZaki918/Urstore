package com.example.urstore.presentation.order.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.urstore.R
import com.example.urstore.data.model.cart.get.ShoppingCart
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White

@Composable
fun ListItemOrder() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN),
        shape = RectangleShape
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            val (productImage, titleText, unitPriceText, captionText, lineDivider, qtyText) = createRefs()


            Image(
                painter = painterResource(R.drawable.drink_1),
                modifier = Modifier
                    .constrainAs(productImage) {
                        start.linkTo(parent.start, SMALL_MARGIN)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(vertical = 12.dp)
                    .size(60.dp),
                contentDescription = ""
            )



                HorizontalDivider(
                    modifier = Modifier
                        .constrainAs(lineDivider) {
                            top.linkTo(productImage.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(horizontal = MEDIUM_MARGIN),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.1f)
                )



            Text(
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(productImage.top, CUSTOM_MARGIN)
                    start.linkTo(productImage.end, SMALL_MARGIN)
                },
                text = "Pumpkin Latte",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.constrainAs(captionText) {
                    top.linkTo(titleText.bottom, VERY_SMALL_MARGIN)
                    start.linkTo(titleText.start)
                },
                text = "Classic",
                color = Color.Gray,
                fontSize = 12.sp
            )



            Text(
                modifier = Modifier.constrainAs(unitPriceText) {
                    end.linkTo(parent.end, MEDIUM_MARGIN)
                    top.linkTo(qtyText.top)
                    bottom.linkTo(qtyText.bottom)
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = "$55.00"
            )


            Text(
                modifier = Modifier.constrainAs(qtyText) {
                    top.linkTo(titleText.top)
                    end.linkTo(parent.end, 70.dp)
                },
                fontSize = 12.sp,
                text = "Qty. 1",
                color = Color.Gray
            )
        }
    }
}