package com.example.urstore.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.ui.theme.Beige_MEDIUM
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.CircleButton
import com.example.urstore.util.CircularLoadingIndicator


@Composable
fun ListItemSearch(
    currentItem: DrinksDataDto,
    onItemClicked: (Int) -> Unit,
    onPlusClicked: (DrinksDataDto) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 6.dp, vertical = SMALL_MARGIN)
            .clickable {
                onItemClicked(currentItem.id)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = SMALL_MARGIN)
        ) {
            val (productBox, titleText, priceText, addButton, loadingBox) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .constrainAs(productBox) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .padding(
                        start = VERY_SMALL_MARGIN,
                        end = VERY_SMALL_MARGIN,
                        top = VERY_SMALL_MARGIN
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Beige_MEDIUM)
            ) {
                AsyncImage(
                    model = currentItem.imageName,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = VERY_SMALL_MARGIN, vertical = VERY_SMALL_MARGIN),
                )
            }


            Text(
                modifier = Modifier
                    .constrainAs(titleText) {
                        start.linkTo(productBox.start)
                        top.linkTo(productBox.bottom, SMALL_MARGIN)
                    }
                    .padding(start = SMALL_MARGIN),
                text = currentItem.title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )


            Text(
                modifier = Modifier
                    .constrainAs(priceText) {
                        start.linkTo(titleText.start)
                        top.linkTo(titleText.bottom)
                    }
                    .padding(start = SMALL_MARGIN, top = SMALL_MARGIN),
                text = "$${currentItem.price}",
                color = Dark_Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            CircularLoadingIndicator(
                isVisible = currentItem.isLoading,
                modifier = Modifier.constrainAs(loadingBox) {
                    top.linkTo(priceText.top)
                    bottom.linkTo(priceText.bottom)
                    end.linkTo(parent.end, SMALL_MARGIN)
                },
                color = Black
            )

            CircleButton(
                isVisible = !currentItem.isLoading,
                floatingActionSize = 20.dp,
                modifier = Modifier.constrainAs(addButton) {
                    top.linkTo(priceText.top)
                    bottom.linkTo(priceText.bottom)
                    end.linkTo(parent.end, SMALL_MARGIN)
                },
                onClicked = {
                    onPlusClicked(currentItem)
                },
                text = "+",
                containerColor = Black,
                contentColor = White
            )
        }
    }
}