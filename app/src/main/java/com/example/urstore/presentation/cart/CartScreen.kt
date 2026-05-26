package com.example.urstore.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.Very_Light_Beige
import com.example.urstore.util.AlertDialog
import com.example.urstore.util.BackButton
import com.example.urstore.util.ButtonShopApp
import com.example.urstore.util.ErrorUi
import com.example.urstore.util.LinearLoadingIndicator
import com.example.urstore.util.RequestState
import com.example.urstore.util.SubTitle
import com.example.urstore.util.UiEffect


@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            if (effect is UiEffect.PobBackStack) {
                navController.popBackStack()
            } else if (effect is UiEffect.Navigate){
                navController.navigate(effect.route)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(Very_Light_Beige)
            .padding(top = EXTRA_LARGE_MARGIN)
    ) {
        CartHeader(
            isCartNotEmpty = uiState.cartResponse?.shoppingCartList?.isNotEmpty() == true &&
                    uiState.cartState == RequestState.SUCCESS,
            onBackClicked = {
                viewModel.onIntent(CartIntent.GoBack)
            },
            onDeleteClicked = {
                viewModel.onIntent(CartIntent.ShowDialog(true))
            }
        )

        CartItems(uiState, viewModel)

        AlertDialog(
            isVisible = uiState.isCartDialogActive,
            title = stringResource(R.string.r_you_sure_delete_cart),
            description = stringResource(R.string.cart_delete_warning),
            confirmTitle = stringResource(R.string.delete),
            dismissTitle = stringResource(R.string.cancel),
            icon = Icons.Outlined.Delete,
            onDismiss = {
                viewModel.onIntent(CartIntent.ShowDialog(false))
            },
            onConfirm = {
                viewModel.apply {
                    onIntent(CartIntent.ShowDialog(false))
                    onIntent(CartIntent.DeleteCart)
                }
            }
        )
    }
}


@Composable
fun CartItems(
    uiState: CartUiState,
    viewModel: CartViewModel
) {
    when (uiState.cartState) {

        RequestState.SUCCESS -> {
            if (uiState.cartResponse?.shoppingCartList?.isNotEmpty() == true) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.60f)
                        .fillMaxWidth()
                        .padding(bottom = LARGE_MARGIN)
                ) {
                    items(
                        uiState.cartResponse.shoppingCartList
                    ) { item ->
                        ListItemCart(
                            currentItem = item,
                            onDeleteClicked = { cartID ->
                                viewModel.onIntent(CartIntent.RemoveItem(cartID))
                            },
                            onDecreaseClicked = { cartId ->
                                viewModel.onIntent(
                                    CartIntent.DecreaseQuantity(cartId)
                                )
                            },
                            onIncreaseClicked = { cartId ->
                                viewModel.onIntent(
                                    CartIntent.IncreaseQuantity(cartId)
                                )
                            }
                        )
                    }
                }
                CheckoutSection(
                    uiState = uiState,
                    onCheckoutPressed = {
                        viewModel.onIntent(CartIntent.GoToCheckout)
                    })

            } else {
                EmptyCartUi()
            }
        }


        RequestState.LOADING ->
            LinearLoadingIndicator(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
            )

        RequestState.ERROR -> ErrorUi(
            modifier = Modifier.wrapContentHeight(),
            topPadding = 150.dp,
            retry = {
                viewModel.onIntent(CartIntent.RetryFetchCart)
            }
        )


        else -> Unit
    }
}


@Composable
fun CartHeader(
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    isCartNotEmpty: Boolean?
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backButton, titleText, deleteText) = createRefs()

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
            text = stringResource(R.string.cart),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )


        if (isCartNotEmpty == true) {
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
fun CheckoutSection(
    uiState: CartUiState,
    onCheckoutPressed: () -> Unit
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (discountRow, checkoutColumn) = createRefs()

        // Discount code
        Row(
            modifier = Modifier
                .constrainAs(discountRow) {
                    bottom.linkTo(checkoutColumn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    horizontal = MEDIUM_MARGIN
                )
                .clip(RoundedCornerShape(LARGE_MARGIN))
                .background(Beige),
            Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(
                    start = MEDIUM_MARGIN,
                    top = MEDIUM_MARGIN,
                    bottom = MEDIUM_MARGIN
                ),
                text = stringResource(R.string.discount_code),
                fontSize = 12.sp
            )


            ButtonShopApp(
                modifier = Modifier.padding(end = VERY_SMALL_MARGIN),
                onButtonClicked = {},
                label = stringResource(R.string.apply)
            )
        }


        // Checkout
        Column(
            modifier = Modifier
                .constrainAs(checkoutColumn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = MEDIUM_MARGIN)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(Beige)
        ) {

            CheckoutItem(
                stringResource(R.string.subtotal),
                "$${uiState.cartResponse?.totalAmount}",
                EXTRA_LARGE_MARGIN
            )

            CheckoutItem(
                stringResource(R.string.delivery),
                "$0.0",
                MEDIUM_MARGIN
            )

            CheckoutItem(
                stringResource(R.string.total_tax),
                "$0.1",
                MEDIUM_MARGIN
            )


            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_MARGIN, vertical = MEDIUM_MARGIN),
                thickness = 1.dp,
                color = Black
            )


            CheckoutItem(
                stringResource(R.string.total),
                "$${(uiState.cartResponse?.totalAmount?.plus(0.1))}",
                0.dp
            )

            ButtonShopApp(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        top = CUSTOM_MARGIN,
                        start = MEDIUM_MARGIN,
                        end = MEDIUM_MARGIN
                    ),
                onButtonClicked = {
                    onCheckoutPressed()
                },
                label = stringResource(R.string.proceed_to_checkout)
            )
        }
    }

}

@Composable
fun CheckoutItem(
    title: String,
    value: String,
    paddingTop: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = paddingTop),
        Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = CUSTOM_MARGIN),
            text = title,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(end = CUSTOM_MARGIN),
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun EmptyCartUi() {
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