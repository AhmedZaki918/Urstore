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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.BIG_MARGIN
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.BackButton
import com.example.urstore.util.homePopularDummy

@Composable
fun SeeAllScreen(navController: NavHostController) {

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
            items(homePopularDummy()) { item ->
                ListItemSeeAll(
                    currentItem = item,
                    onItemClicked = { id ->
                        navController.navigate("${Screen.DETAIL_SCREEN.route}/${id}")
                    }
                )
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