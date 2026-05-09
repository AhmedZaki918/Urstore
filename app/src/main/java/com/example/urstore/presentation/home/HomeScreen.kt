package com.example.urstore.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.data.model.HomeCategory
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.util.ErrorUi
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.MyFloatingActionButton
import com.example.urstore.util.OfferBanner
import com.example.urstore.util.ProductIntent
import com.example.urstore.util.ProductSharedViewModel
import com.example.urstore.util.RequestState
import com.example.urstore.util.SearchBar
import com.example.urstore.util.SnackBar
import com.example.urstore.util.SubTitle
import com.example.urstore.util.Title
import com.example.urstore.util.UiEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    productSharedViewModel: ProductSharedViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            if (effect is UiEffect.ShowSnackbar) {
                snackbarHostState.showSnackbar(
                    message = effect.message,
                    actionLabel = effect.actionLabel,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 130.dp)
        ) {

            item(
                span = { GridItemSpan(maxCurrentLineSpan) }
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    HomeHeader(uiState)
                    HomeCategoryUi(
                        categories = uiState.homeCategories,
                        onItemClicked = { id ->
                            viewModel.onIntent(
                                HomeIntent.OnCategoryClicked(id)
                            )
                            navController.navigate(Screen.SEE_ALL_SCREEN.route)
                        }
                    )
                    PopularTitle()
                }
            }

            popularCoffees(
                uiState,
                viewModel,
                productSharedViewModel,
                navController
            )
        }

        SnackBar(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        )
    }
}


fun LazyGridScope.popularCoffees(
    uiState: HomeUiState,
    viewModel: HomeViewModel,
    productSharedViewModel: ProductSharedViewModel,
    navController: NavHostController
) {
    when (uiState.homeState) {
        RequestState.SUCCESS -> {
            if (!uiState.popularResponse.isNullOrEmpty()) {
                itemsIndexed(uiState.popularResponse) { index, popularItem ->
                    if (index < 2)
                        ListItemPopular(
                            currentItem = popularItem,
                            onItemClicked = {
                                productSharedViewModel.onIntent(
                                    ProductIntent.OnProductClicked(popularItem)
                                )
                                navController.navigate(Screen.DETAIL_SCREEN.route)
                            },
                            onPlusClicked = { product ->
                                viewModel.onIntent(
                                    HomeIntent.AddToCart(product)
                                )
                            }
                        )
                }
            }
        }

        RequestState.ERROR -> {
            item(
                span = { GridItemSpan(maxCurrentLineSpan) }
            ) {
                ErrorUi(
                    isErrorIconVisible = false,
                    retry = {
                        viewModel.onIntent(HomeIntent.RetryHome)
                    })
            }
        }

        RequestState.LOADING -> {
            item(
                span = { GridItemSpan(maxCurrentLineSpan) }
            ) {
                LinearLoadingIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        else -> Unit
    }
}


@Composable
fun HomeHeader(uiState: HomeUiState) {
    var searchQuery by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (notificationBtn, profileImage, usernameText, settingsBtn, searchBar, offerImage) = createRefs()

        MyFloatingActionButton(
            modifier = Modifier.constrainAs(notificationBtn) {
                top.linkTo(parent.top, EXTRA_LARGE_MARGIN)
                end.linkTo(parent.end, MEDIUM_MARGIN)
            },
            icon = R.drawable.bell_icon,
            onClicked = {
            }
        )

        MyFloatingActionButton(
            modifier = Modifier.constrainAs(settingsBtn) {
                top.linkTo(notificationBtn.bottom, MEDIUM_MARGIN)
                end.linkTo(parent.end, MEDIUM_MARGIN)
            },
            icon = R.drawable.settings,
            iconPadding = 11.dp,
            onClicked = {
            }
        )


        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(profileImage) {
                    start.linkTo(parent.start, MEDIUM_MARGIN)
                    top.linkTo(parent.top, LARGE_MARGIN)
                }
        )

        Text(
            text = uiState.firstName + " " + uiState.lastName,
            color = Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .constrainAs(usernameText) {
                    start.linkTo(profileImage.end, SMALL_MARGIN)
                    top.linkTo(profileImage.top)
                    bottom.linkTo(profileImage.bottom)
                }
        )



        SearchBar(
            modifier = Modifier.constrainAs(searchBar) {
                top.linkTo(settingsBtn.top)
                bottom.linkTo(settingsBtn.bottom)
                start.linkTo(parent.start)
                end.linkTo(settingsBtn.start)
            },
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
            },
            onSearch = {

            }
        )


        OfferBanner(
            modifier = Modifier
                .constrainAs(offerImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(searchBar.bottom)
                },
            image = R.drawable.cup,
            title = "Coffee Order Time 8:00 am - 3:00 pm",
            description = "Buy one,Get one for Free",
            buttonText = stringResource(R.string.order_now),
            onBannerClicked = {
            }
        )
    }
}

@Composable
fun HomeCategoryUi(
    categories: List<HomeCategory>,
    onItemClicked: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Title(
            modifier = Modifier.padding(start = MEDIUM_MARGIN, top = SMALL_MARGIN),
            id = R.string.category,
        )


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = MEDIUM_MARGIN, top = SMALL_MARGIN)
        ) {
            items(categories) { category ->
                ListItemCategory(
                    currentIem = category,
                    onItemClicked = { id ->
                        onItemClicked(id)
                    }
                )
            }
        }
    }
}


@Composable
fun PopularTitle() {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (titleText, subTitleText) = createRefs()

        Title(
            modifier = Modifier
                .constrainAs(titleText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN),
            id = R.string.popular_coffees,
        )

        SubTitle(
            modifier = Modifier
                .constrainAs(subTitleText) {
                    top.linkTo(titleText.top)
                    bottom.linkTo(titleText.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = MEDIUM_MARGIN, top = MEDIUM_MARGIN),
            id = R.string.see_all,
        )
    }
}