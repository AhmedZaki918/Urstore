package com.example.urstore.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.urstore.R
import com.example.urstore.data.model.HomeCategory
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.util.MyFloatingActionButton
import com.example.urstore.util.OfferBanner
import com.example.urstore.util.SearchBar

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            HomeHeader()
            HomeCategoryUi(
                categories = uiState.homeCategories,
                onItemClicked = { id ->
                    viewModel.onIntent(
                        HomeIntent.OnCategoryClicked(id)
                    )
                }
            )
        }
    }
}


@Composable
fun HomeHeader() {
    val context = LocalContext.current
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
                Toast.makeText(
                    context,
                    "Notification Pressed",
                    Toast.LENGTH_SHORT
                ).show()
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
                Toast.makeText(
                    context,
                    "Settings Pressed",
                    Toast.LENGTH_SHORT
                ).show()
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
            text = "Tina Anderson",
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
                    top.linkTo(searchBar.bottom, SMALL_MARGIN)
                },
            image = R.drawable.cup,
            title = "Coffee Order Time 8:00 am - 3:00 pm",
            description = "Buy one,Get one for Free",
            buttonText = "Order Now",
            onBannerClicked = {
                Toast.makeText(
                    context,
                    "Order Pressed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        )
    }
}

@Composable
fun HomeCategoryUi(
    categories: List<HomeCategory>,
    onItemClicked: (Int) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            modifier = Modifier.padding(start = MEDIUM_MARGIN, top = SMALL_MARGIN),
            text = "Category",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN)
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