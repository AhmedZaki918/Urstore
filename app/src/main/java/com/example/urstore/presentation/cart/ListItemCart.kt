package com.example.urstore.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.urstore.data.model.cart.get.ShoppingCart
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.CircleButton
import com.example.urstore.util.CircularLoadingIndicator
import com.example.urstore.util.QtyButton


@Composable
fun ListItemCart(
    currentItem: ShoppingCart,
    onDeleteClicked: (Int) -> Unit,
    onIncreaseClicked: (Int) -> Unit,
    onDecreaseClicked: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = MEDIUM_MARGIN, vertical = 10.dp),
        shape = RoundedCornerShape(MEDIUM_MARGIN)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            val (productImage, titleText, unitPriceText, totalPriceText, deleteButton, qtyRow, loading) = createRefs()


            AsyncImage(
                model = currentItem.imageName,
                modifier = Modifier
                    .constrainAs(productImage) {
                        start.linkTo(parent.start, SMALL_MARGIN)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(vertical = SMALL_MARGIN),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(parent.top, SMALL_MARGIN)
                    start.linkTo(productImage.end, MEDIUM_MARGIN)
                },
                text = currentItem.itemName,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.constrainAs(unitPriceText) {
                    start.linkTo(titleText.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                fontSize = 12.sp,
                text = "$${currentItem.itemPrice}"
            )

            Text(
                modifier = Modifier.constrainAs(totalPriceText) {
                    start.linkTo(unitPriceText.start)
                    bottom.linkTo(parent.bottom, SMALL_MARGIN)
                },
                color = Dark_Yellow,
                text = "$${currentItem.totalPrice}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )


            CircularLoadingIndicator(
                isVisible = currentItem.removeIconState,
                modifier = Modifier.constrainAs(loading) {
                    end.linkTo(parent.end, SMALL_MARGIN)
                    top.linkTo(parent.top, SMALL_MARGIN)
                },
                color = Black
            )

            CircleButton(
                isVisible = !currentItem.removeIconState,
                modifier = Modifier.constrainAs(deleteButton) {
                    end.linkTo(parent.end, SMALL_MARGIN)
                    top.linkTo(parent.top, SMALL_MARGIN)
                },
                onClicked = {
                    onDeleteClicked(currentItem.cartId)
                },
                text = "x",
                containerColor = Black,
                contentColor = White,
                floatingActionSize = 25.dp,
                textFontSize = 16.sp,
                floatingDefaultElevation = 0.dp
            )


            Row(
                modifier = Modifier
                    .constrainAs(qtyRow) {
                        bottom.linkTo(parent.bottom, SMALL_MARGIN)
                        end.linkTo(parent.end, SMALL_MARGIN)
                    }
                    .border(
                        width = 1.dp,
                        color = Black.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(CUSTOM_MARGIN)
                    )
                    .padding(horizontal = SMALL_MARGIN, vertical = 4.dp),
            ) {
                CircularLoadingIndicator(
                    isVisible = currentItem.minusIconState,
                    size = 18.dp,
                    color = Black
                )

                QtyButton(
                    isVisible = !currentItem.minusIconState,
                    text = "-",
                    onButtonClicked = {
                        currentItem.count?.let { count ->
                            if (count > 1){
                                onDecreaseClicked(currentItem.cartId)
                            }
                        }
                    })


                Text(
                    modifier = Modifier.padding(horizontal = CUSTOM_MARGIN),
                    text = currentItem.count.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )


                CircularLoadingIndicator(
                    isVisible = currentItem.plusIconState,
                    size = 18.dp,
                    color = Black
                )

                QtyButton(
                    isVisible = !currentItem.plusIconState,
                    text = "+",
                    onButtonClicked = {
                        onIncreaseClicked(currentItem.cartId)
                    })
            }
        }
    }
}