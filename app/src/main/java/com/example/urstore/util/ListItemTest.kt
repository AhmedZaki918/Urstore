package com.example.urstore.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.urstore.ui.theme.MEDIUM_MARGIN

@Composable
fun ListItemTest(
    currentIndex: Int,
    itemsCount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(
                start = MEDIUM_MARGIN,
                end = MEDIUM_MARGIN,
                bottom = if (currentIndex == itemsCount - 1) 0.dp else 10.dp
            ),
        shape = when (currentIndex) {
            0 -> RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
            (itemsCount - 1) -> RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp)
            else -> RoundedCornerShape(MEDIUM_MARGIN)
        }
    ) {
        // list item ui..



    }
}



@Composable
fun Header(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(
            topStart = MEDIUM_MARGIN,
            topEnd = MEDIUM_MARGIN
        ),
    ) {
        Text(
            modifier = Modifier.padding(
                start = MEDIUM_MARGIN,
                top = MEDIUM_MARGIN
            ),
            text = "Your order",
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun Footer(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(
            bottomStart  = MEDIUM_MARGIN,
            bottomEnd = MEDIUM_MARGIN
        ),
    ) {
        Text(
            modifier = Modifier.padding(
                start = MEDIUM_MARGIN,
                top = MEDIUM_MARGIN
            ),
            text = "Sub Total = 1000 EGP",
            fontWeight = FontWeight.Bold
        )
    }
}