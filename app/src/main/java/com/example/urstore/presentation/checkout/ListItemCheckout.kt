package com.example.urstore.presentation.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.urstore.R
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.QtyButton


@Composable
fun ListItemCheckout() {

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
            val (productImage, titleText, unitPriceText, captionText, lineDivider, qtyRow) = createRefs()


            Image(
                painter = painterResource(R.drawable.drink_4),
                modifier = Modifier
                    .constrainAs(productImage) {
                        start.linkTo(parent.start, SMALL_MARGIN)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(vertical = 12.dp)
                    .size(60.dp),
                contentDescription = "",
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
                text = "Medium",
                color = Color.Gray,
                fontSize = 12.sp
            )



            Text(
                modifier = Modifier.constrainAs(unitPriceText) {
                    end.linkTo(parent.end, MEDIUM_MARGIN)
                    top.linkTo(qtyRow.top)
                    bottom.linkTo(qtyRow.bottom)
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = "$35"
            )




            Row(
                modifier = Modifier
                    .constrainAs(qtyRow) {
                        top.linkTo(titleText.top)
                        end.linkTo(parent.end, 70.dp)
                    }
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = SMALL_MARGIN, vertical = 6.dp),
            ) {


                QtyButton(
                    fontSize = 16.sp,
                    text = "-",
                    onButtonClicked = {}
                )


                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "1",
                    fontWeight = FontWeight.Bold,
                )



                QtyButton(
                    fontSize = 16.sp,
                    text = "+",
                    onButtonClicked = {}
                )
            }
        }
    }

}