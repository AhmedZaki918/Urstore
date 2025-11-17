package com.example.urstore.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.urstore.R
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Brown
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.White


@Composable
fun ButtonShopApp(
    modifier: Modifier,
    label: String,
    isVisible: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    onButtonClicked: () -> Unit
) {
    if (isVisible) {
        Button(
            onClick = {
                onButtonClicked()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red.copy(0.8f),
                contentColor = Color.White
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                label.uppercase(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun MyFloatingActionButton(
    modifier: Modifier,
    onClicked: () -> Unit,
    icon: Int,
    iconPadding: Dp = 0.dp
) {
    FloatingActionButton(
        modifier = modifier.size(45.dp),
        onClick = onClicked,
        containerColor = Brown,
        contentColor = Color.White,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(iconPadding),
        )
    }
}


@Composable
fun CircleButton(
    modifier: Modifier,
    onClicked: () -> Unit,
    text: String
) {
    FloatingActionButton(
        modifier = modifier.size(30.dp),
        onClick = onClicked,
        containerColor = White,
        contentColor = Black,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}


@Composable
fun SearchBar(
    query: String,
    modifier: Modifier,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = query,

        onValueChange = onQueryChange,
        modifier = modifier
            .wrapContentWidth()
            .padding(8.dp),
        placeholder = {
            Text(stringResource(R.string.search_anything))
        },
        shape = RoundedCornerShape(LARGE_MARGIN),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        )
    )
}


@Composable
fun OfferBanner(
    modifier: Modifier,
    onBannerClicked: () -> Unit,
    image: Int,
    title: String,
    description: String,
    buttonText: String,
    backgroundColor: Color = Light_Brown
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (offerImage, titleText, descriptionText, orderButton) = createRefs()

            Image(
                painter = painterResource(image),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(offerImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
            )

            Text(
                text = title,
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(offerImage.end, MEDIUM_MARGIN)
                    end.linkTo(parent.end, SMALL_MARGIN)
                    top.linkTo(parent.top, MEDIUM_MARGIN)
                },
                color = White,
                fontSize = 12.sp
            )


            Text(
                text = description,
                modifier = Modifier
                    .constrainAs(descriptionText) {
                        start.linkTo(offerImage.end)
                        end.linkTo(parent.end)
                        top.linkTo(titleText.bottom, 12.dp)
                    }
                    .width(180.dp),
                overflow = TextOverflow.Ellipsis,
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )


            Button(
                modifier = Modifier
                    .constrainAs(orderButton) {
                        start.linkTo(offerImage.end)
                        end.linkTo(parent.end)
                        top.linkTo(descriptionText.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Dark_Yellow,
                    contentColor = White
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    onBannerClicked()
                },
            ) {
                Text(
                    text = buttonText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun Title(
    @StringRes id: Int,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun SubTitle(
    @StringRes id: Int,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id),
        fontSize = 14.sp
    )
}


fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(
        this,
        message,
        duration
    ).show()
}