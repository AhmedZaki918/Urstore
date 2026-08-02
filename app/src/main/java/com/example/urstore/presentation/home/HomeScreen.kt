package com.example.urstore.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.urstore.R
import com.example.urstore.data.model.categories.CategoriesDto
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.util.AlertDialog
import com.example.urstore.util.ErrorUi
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.MyFloatingActionButton
import com.example.urstore.util.OfferBanner
import com.example.urstore.util.ProductIntent
import com.example.urstore.util.ProductSharedViewModel
import com.example.urstore.util.RequestState
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

                else -> Unit
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
    ) {

        when (uiState.homeState) {
            RequestState.LOADING -> {
                LinearLoadingIndicator(
                    modifier = Modifier.fillMaxSize()
                )
            }

            RequestState.ERROR -> {
                ErrorUi(
                    modifier = Modifier.fillMaxSize(),
                    retry = {
                        viewModel.onIntent(HomeIntent.RetryHome)
                    })
            }

            RequestState.SUCCESS -> {
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
                            HomeHeader(uiState, viewModel)
                            HomeCategoryUi(
                                categories = uiState.homeCategories,
                                onItemClicked = { id ->
                                    viewModel.apply {
                                        onIntent(HomeIntent.OnCategoryClicked(id))
                                    }
                                }
                            )
                            PopularTitle()
                        }
                    }

                    popularCoffees(
                        uiState,
                        viewModel,
                        productSharedViewModel
                    )
                }
            }

            else -> Unit
        }


        AlertDialog(
            isVisible = uiState.isLoginDialogActive,
            title = stringResource(R.string.should_login),
            description = stringResource(R.string.returned_to_login),
            confirmTitle = stringResource(R.string.login),
            dismissTitle = stringResource(R.string.cancel),
            icon = Icons.Outlined.Login,
            onDismiss = {
                viewModel.onIntent(HomeIntent.ShowDialog(false))
            },
            onConfirm = {
                viewModel.apply {
                    onIntent(HomeIntent.ShowDialog(false))
                    onIntent(HomeIntent.Login)
                }
            }
        )

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
    productSharedViewModel: ProductSharedViewModel
) {
    if (!uiState.popularResponse.isNullOrEmpty()) {
        item(
            span = { GridItemSpan(maxCurrentLineSpan) }
        ){
            Spacer(modifier = Modifier.height(SMALL_MARGIN))
        }

        itemsIndexed(uiState.popularResponse) { index, popularItem ->
            if (index < 2){
                ListItemPopular(
                    currentItem = popularItem,
                    onItemClicked = {
                        productSharedViewModel.onIntent(
                            ProductIntent.OnProductClicked(popularItem)
                        )
                        viewModel.onIntent(HomeIntent.GoToDetails(popularItem.id))
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
}


@Composable
fun HomeHeader(
    uiState: HomeUiState,
    viewModel: HomeViewModel
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (searchBtn, profileImage, usernameText, offerImage) = createRefs()

        MyFloatingActionButton(
            modifier = Modifier.constrainAs(searchBtn) {
                top.linkTo(parent.top, EXTRA_LARGE_MARGIN)
                end.linkTo(parent.end, MEDIUM_MARGIN)
            },
            icon = R.drawable.search_icon,
            onClicked = {
                viewModel.onIntent(HomeIntent.Search)
            }
        )


        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(profileImage) {
                    start.linkTo(parent.start, MEDIUM_MARGIN)
                    top.linkTo(parent.top, LARGE_MARGIN)
                }.padding(bottom = MEDIUM_MARGIN)
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

        //  This offer banner {placeholder} is temporary
        OfferBanner(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(offerImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(searchBtn.bottom)
                }
                .padding(
                    start = SMALL_MARGIN,
                    end = SMALL_MARGIN,
                    top = CUSTOM_MARGIN,
                    bottom = MEDIUM_MARGIN
                ),
            image = R.drawable.cup,
            title = "Coffee Order Time 8:00 am - 3:00 pm",
            description = "Buy one, Get one for Free",
            buttonText = stringResource(R.string.order_now)
        )

//        AsyncImage(
//            modifier = Modifier
//                .fillMaxWidth()
//                .constrainAs(offerImage) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(searchBtn.bottom)
//                }
//                .padding(
//                    start = MEDIUM_MARGIN,
//                    end = MEDIUM_MARGIN,
//                    top = LARGE_MARGIN,
//                    bottom = MEDIUM_MARGIN
//                ),
//            model = uiState.offersResponse?.get(0)?.imageName,
//            contentDescription = "offer image",
//            contentScale = ContentScale.Crop
//        )
    }
}

@Composable
fun HomeCategoryUi(
    categories: List<CategoriesDto>?,
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
            fontSize = 16.sp
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = MEDIUM_MARGIN, top = SMALL_MARGIN)
        ) {
            if (!categories.isNullOrEmpty()) {
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
            fontSize = 16.sp
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