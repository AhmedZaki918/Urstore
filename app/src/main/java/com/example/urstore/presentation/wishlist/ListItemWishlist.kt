package com.example.urstore.presentation.wishlist


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.example.urstore.data.local.CoffeeEntity
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.CircleButton


@Composable
fun ListItemWishlist(
    currentItem: CoffeeEntity,
    onRemoveClicked: (CoffeeEntity) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN, vertical = 10.dp),
        shape = RoundedCornerShape(MEDIUM_MARGIN)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            val (productImage, titleText, unitPriceText, deleteButton, captionText) = createRefs()


            AsyncImage(
                model = currentItem.itemImage,
                modifier = Modifier
                    .constrainAs(productImage) {
                        start.linkTo(parent.start, SMALL_MARGIN)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(vertical = SMALL_MARGIN)
                    .size(80.dp),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(parent.top, SMALL_MARGIN)
                    start.linkTo(productImage.end, MEDIUM_MARGIN)
                },
                text = currentItem.name,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .constrainAs(captionText) {
                        top.linkTo(titleText.bottom, SMALL_MARGIN)
                        start.linkTo(productImage.end, MEDIUM_MARGIN)
                    },
                text = currentItem.caption,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.constrainAs(unitPriceText) {
                    start.linkTo(titleText.start)
                    top.linkTo(captionText.bottom, SMALL_MARGIN)
                },
                text = "$${currentItem.price}",
                color = Dark_Yellow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )



            CircleButton(
                modifier = Modifier.constrainAs(deleteButton) {
                    end.linkTo(parent.end, SMALL_MARGIN)
                    top.linkTo(parent.top, SMALL_MARGIN)
                },
                onClicked = {
                    onRemoveClicked(currentItem)
                },
                text = "x",
                containerColor = Black,
                contentColor = White,
                floatingActionSize = 25.dp,
                textFontSize = 16.sp,
                floatingDefaultElevation = 0.dp
            )
        }
    }
}