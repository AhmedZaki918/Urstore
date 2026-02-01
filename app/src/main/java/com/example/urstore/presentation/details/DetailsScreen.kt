package com.example.urstore.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.urstore.R
import com.example.urstore.data.model.ItemDetails
import com.example.urstore.data.model.ProductSize
import com.example.urstore.presentation.home.HomeViewModel
import com.example.urstore.ui.theme.BIG_MARGIN
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.White
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.QtyButton
import com.example.urstore.util.RequestState
import com.example.urstore.util.SizeShape
import com.example.urstore.util.Title
import com.example.urstore.util.toast

@Composable
fun DetailsScreen(
    homeViewModel: HomeViewModel ,
    detailsViewModel: DetailsViewModel,
    navController: NavHostController
) {
    val uiState = detailsViewModel.uiState.collectAsState().value
    val product = homeViewModel.uiState.collectAsState().value.itemDetails
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    UpdateAddToCartState(
        viewModel = detailsViewModel,
        uiState = uiState,
        onSuccess = {
            context.toast(stringResource(R.string.added_to_cart))
        },
        onError = {
            context.toast(stringResource(R.string.already_exist))
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(White)
    ) {
        DetailsHeader(
            navController,
            product
        )
        ProductSizeBar(
            uiState.productSize,
            onItemClicked = {
                detailsViewModel.onIntent(
                    DetailsIntent.OnSizeClicked(it)
                )
            }
        )
        QtyAndRating(product)
        Description(product)
        AddToCart(
            popularItem = product,
            onAddToCartClicked = {
                //viewModel.onIntent(DetailsIntent.AddToCart(uiState.popularItem))
            }
        )
    }
}


@Composable
fun DetailsHeader(
    navController: NavHostController,
    productDetails: ItemDetails
) {

    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val (backRow, favoriteButton, productImage, tileText) = createRefs()

        BackButton(
            modifier = Modifier.constrainAs(backRow) {
                top.linkTo(favoriteButton.top)
                bottom.linkTo(favoriteButton.bottom)
                start.linkTo(parent.start, MEDIUM_MARGIN)
            },
            onBackClicked = {
                navController.popBackStack()
            }
        )

        IconButton(
            modifier = Modifier.constrainAs(favoriteButton) {
                top.linkTo(parent.top, LARGE_MARGIN)
                end.linkTo(parent.end, MEDIUM_MARGIN)
            },
            onClick = {

            }) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = ""
            )
        }


        AsyncImage(
            model = productDetails.imageName,
            contentDescription = "",
            modifier = Modifier.constrainAs(productImage) {
                top.linkTo(parent.top, BIG_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )


        Title(
            modifier = Modifier.constrainAs(tileText) {
                top.linkTo(productImage.bottom, CUSTOM_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = productDetails.title,
            fontSize = 24.sp
        )
    }
}


@Composable
fun ProductSizeBar(
    productSize: List<ProductSize>,
    onItemClicked: (Int) -> Unit
) {
    Column(modifier = Modifier.wrapContentSize()) {
        Title(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = MEDIUM_MARGIN, top = LARGE_MARGIN),
            id = R.string.coffee_size
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CUSTOM_MARGIN, vertical = MEDIUM_MARGIN)
                .background(
                    color = Color.LightGray.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(CUSTOM_MARGIN)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            SizeShape(
                modifier = Modifier.weight(1f),
                currentItem = productSize[0],
                onItemClicked = {
                    onItemClicked(productSize[0].id)
                }
            )

            SizeShape(
                modifier = Modifier.weight(1f),
                currentItem = productSize[1],
                onItemClicked = {
                    onItemClicked(productSize[1].id)
                }
            )

            SizeShape(
                modifier = Modifier.weight(1f),
                currentItem = productSize[2],
                onItemClicked = {
                    onItemClicked(productSize[2].id)
                }
            )
        }
    }
}


@Composable
fun QtyAndRating(popularItem: ItemDetails) {
    var quantityState by remember {
        mutableIntStateOf(1)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = SMALL_MARGIN)
    ) {
        val (qtyRow, qtyText, ratingImage, ratingText) = createRefs()


        Text(
            modifier = Modifier.constrainAs(qtyText) {
                start.linkTo(parent.start, MEDIUM_MARGIN)
                top.linkTo(parent.top, SMALL_MARGIN)
            },
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.qty),
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier
                .constrainAs(qtyRow) {
                    top.linkTo(qtyText.top)
                    bottom.linkTo(qtyText.bottom)
                    start.linkTo(qtyText.end, SMALL_MARGIN)
                }
                .border(
                    width = 1.dp,
                    color = Black,
                    shape = RoundedCornerShape(CUSTOM_MARGIN)
                )
                .padding(horizontal = SMALL_MARGIN, vertical = 6.dp),
        ) {

            QtyButton(
                text = "-",
                onButtonClicked = {
                    if (quantityState > 1) {
                        quantityState--
                    }
                })

            Text(
                modifier = Modifier.padding(horizontal = CUSTOM_MARGIN),
                text = quantityState.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp

            )

            QtyButton(
                text = "+",
                onButtonClicked = {
                    quantityState++
                })
        }

        Image(
            painter = painterResource(R.drawable.star),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(ratingImage) {
                    end.linkTo(parent.end, MEDIUM_MARGIN)
                    bottom.linkTo(qtyRow.bottom)
                    top.linkTo(qtyRow.top)
                }
        )


        Text(
            modifier = Modifier.constrainAs(ratingText) {
                end.linkTo(ratingImage.start, MEDIUM_MARGIN)
                bottom.linkTo(ratingImage.bottom)
                top.linkTo(ratingImage.top)
            },
            fontWeight = FontWeight.Bold,
            text = popularItem.rate.toString()
        )
    }
}

@Composable
fun Description(popularItem: ItemDetails) {
    Title(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = MEDIUM_MARGIN, top = LARGE_MARGIN),
        id = R.string.description
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_MARGIN, vertical = SMALL_MARGIN),
        text = popularItem.description
    )
}


@Composable
fun AddToCart(
    popularItem: ItemDetails,
    onAddToCartClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = MEDIUM_MARGIN, vertical = MEDIUM_MARGIN),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        ButtonShopApp(
            modifier = Modifier.wrapContentSize(),
            onButtonClicked = {
                onAddToCartClicked()
            },
            label = stringResource(R.string.add_to_cart)
        )

        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = MEDIUM_MARGIN),
            text = "$${popularItem.price}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun UpdateAddToCartState(
    uiState: DetailsUiState,
    onSuccess: @Composable () -> Unit,
    onError: @Composable () -> Unit,
    viewModel: DetailsViewModel
) {
    if (uiState.addedToCartState == RequestState.SUCCESS) {
        onSuccess.invoke()
    } else if (uiState.addedToCartState == RequestState.ERROR) {
        onError.invoke()
    }
    viewModel.onIntent(DetailsIntent.RevertAddedToCartStateToIdle)
}