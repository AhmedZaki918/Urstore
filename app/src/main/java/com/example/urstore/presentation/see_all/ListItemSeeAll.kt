package com.example.urstore.presentation.see_all

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.CircleButton

@Composable
fun ListItemSeeAll(
    currentItem: DrinksDataDto,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MEDIUM_MARGIN,
                end = MEDIUM_MARGIN,
                top = MEDIUM_MARGIN
            )
            .wrapContentHeight()
            .clickable {
                onItemClicked(currentItem.id)
            }
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(top = 60.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(MEDIUM_MARGIN)
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
            ) {
                val (titleText, descriptionText, priceText, addButton) = createRefs()

                Text(
                    modifier = Modifier.constrainAs(titleText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, 80.dp)
                    },
                    text = currentItem.title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(descriptionText) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(titleText.bottom, VERY_SMALL_MARGIN)
                        }
                        .padding(horizontal = MEDIUM_MARGIN),
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    text = currentItem.description,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )


                Text(
                    modifier = Modifier.constrainAs(priceText) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(addButton.top)
                        bottom.linkTo(addButton.bottom)
                    },
                    text = "$${currentItem.price}",
                    color = Dark_Yellow,
                    fontWeight = FontWeight.Bold
                )

                CircleButton(
                    modifier = Modifier.constrainAs(addButton) {
                        bottom.linkTo(parent.bottom, SMALL_MARGIN)
                        end.linkTo(parent.end, SMALL_MARGIN)
                    },
                    onClicked = {

                    },
                    text = "+",
                    containerColor = Black,
                    contentColor = White
                )
            }
        }

        AsyncImage(
            model = currentItem.imageName,
            contentDescription = "",
            modifier = Modifier.padding(horizontal = MEDIUM_MARGIN),
        )
    }
}