package com.example.urstore.presentation.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.SubTitle

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
        WishlistHeader(
            isFavoriteNotEmpty = uiState.drinks.isNotEmpty(),
            onDeleteClicked = {
                viewModel.onIntent(WishlistIntent.DeleteAll)
            }
        )

        Spacer(modifier = Modifier.height(MEDIUM_MARGIN))

        if (uiState.drinks.isNotEmpty()) {
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
        } else {
            EmptyFavoriteUi()
        }
    }
}


@Composable
fun WishlistHeader(
    isFavoriteNotEmpty: Boolean,
    onDeleteClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 40.dp)
    ) {
        val (titleText, deleteText) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = stringResource(R.string.favorite),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )


        if (isFavoriteNotEmpty) {
            Text(
                modifier = Modifier
                    .constrainAs(deleteText) {
                        end.linkTo(parent.end)
                        top.linkTo(titleText.top)
                        bottom.linkTo(titleText.bottom)
                    }
                    .padding(end = MEDIUM_MARGIN)
                    .wrapContentSize()
                    .clickable {
                        onDeleteClicked()
                    },
                text = stringResource(R.string.remove),
                color = Brown
            )
        }
    }
}


@Composable
fun EmptyFavoriteUi() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.no_cart),
                contentDescription = "Empty cart icon"
            )

            SubTitle(
                id = R.string.no_data_found,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }
    }
}