package com.example.urstore.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urstore.ui.theme.Beige

@Composable
fun ProfileScreen(){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {

    }
}