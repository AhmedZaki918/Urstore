package com.example.urstore.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.urstore.R
import com.example.urstore.data.model.drinks.DrinksDataDto
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.SearchBackground
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.util.AlertDialog
import com.example.urstore.util.BackButton
import com.example.urstore.util.ErrorUi
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.ProductIntent
import com.example.urstore.util.ProductSharedViewModel
import com.example.urstore.util.SnackBar
import com.example.urstore.util.UiEffect

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    productSharedViewModel: ProductSharedViewModel,
    navController: NavHostController
) {
    val searchResults = viewModel.drinks.collectAsLazyPagingItems()
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is UiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = effect.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEffect.Navigate -> {
                    navController.navigate(route = effect.route)
                }

                is UiEffect.PobBackStack -> {
                    navController.popBackStack()
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .navigationBarsPadding()
            .background(SearchBackground)
            .padding(top = EXTRA_LARGE_MARGIN),
    ) {
        Column(modifier = Modifier.wrapContentSize()) {
            SearchHeader {
                viewModel.onIntent(SearchIntent.GoBack)
            }

            SearchBar(
                query = uiState.searchKeyword,
                onQueryChange = { keyword ->
                    viewModel.onIntent(SearchIntent.Search(keyword))
                },
                onClearClick = {
                    viewModel.onIntent(SearchIntent.ClearSearch)
                },
            )

            SearchResults(
                uiState,
                searchResults,
                viewModel,
                productSharedViewModel
            )
        }

        AlertDialog(
            isVisible = uiState.isLoginDialogActive,
            title = stringResource(R.string.should_login),
            description = stringResource(R.string.returned_to_login),
            confirmTitle = stringResource(R.string.login),
            dismissTitle = stringResource(R.string.cancel),
            icon = Icons.Outlined.Login,
            onDismiss = {
                viewModel.onIntent(SearchIntent.ShowDialog(false))
            },
            onConfirm = {
                viewModel.apply {
                    onIntent(SearchIntent.ShowDialog(false))
                    onIntent(SearchIntent.Login)
                }
            }
        )

        SnackBar(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = MEDIUM_MARGIN)
        )
    }
}

@Composable
fun SearchResults(
    uiState: SearchUiState,
    drinks: LazyPagingItems<DrinksDataDto>,
    viewModel: SearchViewModel,
    productSharedViewModel: ProductSharedViewModel
) {
    val refreshState = drinks.loadState.refresh

    when (refreshState) {
        is LoadState.Loading -> {
            LinearLoadingIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LARGE_MARGIN)
            )
        }

        is LoadState.Error -> {
            ErrorUi(
                modifier = Modifier.fillMaxSize(),
                retry = {
                    drinks.retry()
                }
            )
        }

        is LoadState.NotLoading -> {
            SearchSummary(
                isVisible = drinks.itemCount > 0,
                modifier = Modifier.padding(
                    MEDIUM_MARGIN,
                    top = MEDIUM_MARGIN
                ),
                count = drinks.itemCount,
                query = uiState.searchKeyword
            )


            if (drinks.itemCount > 0) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = MEDIUM_MARGIN, start = SMALL_MARGIN, end = SMALL_MARGIN),
                    contentPadding = PaddingValues(bottom = EXTRA_LARGE_MARGIN)
                ) {
                    items(drinks.itemCount) { index ->
                        drinks[index]?.let { item ->
                            ListItemSearch(
                                currentItem = item,
                                onItemClicked = {
                                    productSharedViewModel.onIntent(
                                        ProductIntent.OnProductClicked(
                                            item
                                        )
                                    )
                                    viewModel.onIntent(SearchIntent.GoToDetails)
                                },
                                onPlusClicked = { product ->
                                    viewModel.onIntent(
                                        SearchIntent.AddToCart(product)
                                    )
                                }
                            )
                        }
                    }
                }
            } else {
                EmptySearchUi(uiState.isSearchInitialized)
            }
        }
    }
}

@Composable
fun SearchHeader(
    onBackClicked: () -> Unit = {}
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
            text = stringResource(R.string.search),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(SMALL_MARGIN))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = MEDIUM_MARGIN, end = MEDIUM_MARGIN),
        shape = RoundedCornerShape(MEDIUM_MARGIN),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        // Search Field
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(SearchBackground)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search),
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            // Clear Button
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.width(8.dp))
}


@Composable
fun SearchSummary(
    isVisible: Boolean,
    modifier: Modifier,
    count: Int,
    query: String
) {
    if (isVisible) {
        Text(
            modifier = modifier,
            text = buildAnnotatedString {
                append("$count Results for ")

                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold)
                ) {
                    append("\"$query\"")
                }
            },
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}


@Composable
fun EmptySearchUi(isSearchInitialized: Boolean) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 150.dp)
    ) {
        val (searchBox, titleText, captionText) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(searchBox) {
                    end.linkTo(titleText.start, SMALL_MARGIN)
                    top.linkTo(titleText.top)
                    bottom.linkTo(captionText.bottom)
                }
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black.copy(alpha = 0.4f)
            )
        }

        Text(
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = if (isSearchInitialized) stringResource(R.string.cant_find_search)
            else stringResource(R.string.try_a_new_search),
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier.constrainAs(captionText) {
                top.linkTo(titleText.bottom, VERY_SMALL_MARGIN)
                start.linkTo(titleText.start)
            },
            text = if (isSearchInitialized) stringResource(R.string.try_a_different_keyword)
            else stringResource(R.string.search_now),
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}