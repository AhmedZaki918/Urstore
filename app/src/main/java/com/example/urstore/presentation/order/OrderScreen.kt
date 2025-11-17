package com.example.urstore.presentation.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urstore.ui.theme.Beige

@Composable
fun OrderScreen(){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {

    }
}