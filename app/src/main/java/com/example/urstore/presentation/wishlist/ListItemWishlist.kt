package com.example.urstore.presentation.wishlist


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import coil.compose.AsyncImage
import com.example.urstore.R
import com.example.urstore.data.local.CoffeeEntity
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Cacy
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Brown
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.Off_White
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.CircularLoadingIndicator
import com.example.urstore.util.LinearLoadingIndicator


@Composable
fun ListItemWishlist(
    currentItem: CoffeeEntity,
    onRemoveClicked: (CoffeeEntity) -> Unit,
    onAddToCart: (Int) -> Unit
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
                .fillMaxWidth()
                .height(140.dp)
                .background(Cacy)
        ) {
            val (productImage, titleText, priceText, deleteButton,
                captionText, ratingImage, ratingText,addToCartBtn,loading) = createRefs()


            AsyncImage(
                model = currentItem.itemImage,
                modifier = Modifier
                    .constrainAs(productImage) {
                        start.linkTo(parent.start, SMALL_MARGIN)
                        top.linkTo(parent.top, MEDIUM_MARGIN)
                    }
                    .size(90.dp),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(parent.top, MEDIUM_MARGIN)
                    start.linkTo(productImage.end, MEDIUM_MARGIN)
                },
                text = currentItem.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .constrainAs(captionText) {
                        top.linkTo(titleText.bottom, SMALL_MARGIN)
                        start.linkTo(productImage.end, MEDIUM_MARGIN)
                    },
                text = currentItem.caption,
                maxLines = 2,
                fontSize = 12.sp
            )



            IconButton(
                modifier = Modifier.constrainAs(deleteButton) {
                    end.linkTo(parent.end, VERY_SMALL_MARGIN)
                    top.linkTo(parent.top, SMALL_MARGIN)
                },
                onClick = {
                    onRemoveClicked(currentItem)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    tint = Brown
                )
            }

            Image(
                modifier = Modifier
                    .constrainAs(ratingImage) {
                        start.linkTo(titleText.start)
                        top.linkTo(captionText.bottom, LARGE_MARGIN)
                    }.size(14.dp),
                painter = painterResource(R.drawable.star),
                contentDescription = ""
            )


            Text(
                modifier = Modifier.constrainAs(ratingText) {
                    start.linkTo(ratingImage.end, SMALL_MARGIN)
                    bottom.linkTo(ratingImage.bottom)
                    top.linkTo(ratingImage.top)
                },
                text = currentItem.rating.toString(),
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.constrainAs(priceText) {
                    start.linkTo(ratingText.end, CUSTOM_MARGIN)
                    top.linkTo(ratingImage.top)
                },
                text = "$${currentItem.price}",
                color = Dark_Yellow,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            LinearLoadingIndicator(
                isVisible = currentItem.isLoading,
                modifier = Modifier.constrainAs(loading) {
                    end.linkTo(parent.end,MEDIUM_MARGIN)
                    top.linkTo(priceText.top)
                    bottom.linkTo(priceText.bottom)
                },
                linearIndicatorWidth = 0.2f
            )

            ButtonShopApp(
                isVisible = !currentItem.isLoading,
                modifier = Modifier.constrainAs(addToCartBtn){
                    end.linkTo(parent.end,MEDIUM_MARGIN)
                    top.linkTo(priceText.top)
                    bottom.linkTo(priceText.bottom)
                },
                textFontSize = 12.sp,
                onButtonClicked = {
                    onAddToCart(currentItem.id)
                },
                label = stringResource(R.string.add_to_cart),
                roundedCornerSize = MEDIUM_MARGIN,
                height = 35.dp
            )
        }
    }
}