package com.example.urstore.presentation.see_all

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.urstore.data.model.drinks_dto.DrinksDataDto
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.ErrorUi
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.ProductIntent
import com.example.urstore.util.ProductSharedViewModel

@Composable
fun SeeAllScreen(
    viewModel: SeeAllViewModel = hiltViewModel(),
    navController: NavHostController,
    productSharedViewModel: ProductSharedViewModel
) {
    val drinks = viewModel.drinks.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .wrapContentSize()
            .navigationBarsPadding()
            .background(Beige)
            .padding(top = EXTRA_LARGE_MARGIN),
    ) {
        SeeAllHeader {
            navController.popBackStack()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = EXTRA_LARGE_MARGIN)
        ) {
            seeAllContent(
                drinks,
                productSharedViewModel,
                navController
            )
        }
    }
}

fun LazyGridScope.seeAllContent(
    drinks: LazyPagingItems<DrinksDataDto>,
    productSharedViewModel: ProductSharedViewModel,
    navController: NavHostController
) {
    val refreshState = drinks.loadState.refresh

    when (refreshState) {
        is LoadState.Loading -> {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                LinearLoadingIndicator(modifier = Modifier.padding(top = SMALL_MARGIN))
            }
        }

        is LoadState.Error -> {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                ErrorUi {
                    drinks.retry()
                }
            }
        }

        is LoadState.NotLoading -> {
            items(drinks.itemCount) { index ->
                drinks[index]?.let { item ->
                    ListItemSeeAll(
                        currentItem = item,
                        onItemClicked = {
                            productSharedViewModel.onIntent(
                                ProductIntent.OnProductClicked(item)
                            )
                            navController.navigate(Screen.DETAIL_SCREEN.route)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SeeAllHeader(
    onBackClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, titleText) = createRefs()

        BackButton(
            modifier = Modifier
                .constrainAs(backButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = MEDIUM_MARGIN),
            onBackClicked = {
                onBackClicked()
            }
        )


        Text(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Cappuccino",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}