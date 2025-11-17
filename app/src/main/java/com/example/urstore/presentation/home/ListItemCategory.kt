package com.example.urstore.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urstore.data.model.HomeCategory
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.White


@Composable
fun ListItemCategory(
    currentIem: HomeCategory,
    onItemClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(end = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (currentIem.isClicked) Brown else White
        ),
        shape = RoundedCornerShape(CUSTOM_MARGIN),
        onClick = {
            onItemClicked(currentIem.id)
        }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(SMALL_MARGIN),
            text = currentIem.category,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = if (currentIem.isClicked) White else Black
        )
    }
}