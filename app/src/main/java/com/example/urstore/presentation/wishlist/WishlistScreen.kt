package com.example.urstore.presentation.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.presentation.checkout.CheckoutViewModel
import com.example.urstore.ui.theme.Beige
import com.example.urstore.util.CartSharedViewModel

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(uiState.drinks) {
                ListItemWishlist(
                    currentItem = it,
                    onRemoveClicked = { item ->
                        viewModel.onIntent(WishlistIntent.RemoveItem(item))
                    }
                )
            }
        }


    }
}