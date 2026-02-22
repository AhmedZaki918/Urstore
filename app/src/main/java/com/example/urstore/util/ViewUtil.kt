package com.example.urstore.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.urstore.R
import com.example.urstore.data.model.ProductSize
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Brown
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.Off_White
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White


@Composable
fun ButtonShopApp(
    modifier: Modifier = Modifier.wrapContentWidth(),
    label: String,
    isVisible: Boolean = true,
    onButtonClicked: () -> Unit
) {
    if (isVisible) {
        Button(
            onClick = {
                onButtonClicked()
            },
            shape = RoundedCornerShape(LARGE_MARGIN),
            colors = ButtonDefaults.buttonColors(
                containerColor = Brown,
                contentColor = Color.White
            ),
            modifier = modifier
        ) {
            Text(
                modifier = Modifier.padding(horizontal = CUSTOM_MARGIN),
                text = label,
                fontSize = 16.sp
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
    text: String,
    containerColor: Color = White,
    contentColor: Color = Black,
    floatingActionSize: Dp = 30.dp,
    textFontSize: TextUnit = 18.sp,
    floatingDefaultElevation: Dp = 8.dp,
) {
    FloatingActionButton(
        modifier = modifier.size(floatingActionSize),
        onClick = onClicked,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = floatingDefaultElevation,
            pressedElevation = 12.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = textFontSize
        )
    }
}


@Composable
fun TextFieldShopApp(
    input: String,
    onInputChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType,
    topPadding : Dp = SMALL_MARGIN
) {
    TextField(
        value = input,
        onValueChange = onInputChange,
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Off_White,
            unfocusedContainerColor = Off_White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = topPadding)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun TextFieldShopApp(
    input: String,
    onInputChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector? = null,
    keyboardType: KeyboardType,
    topPadding : Dp = SMALL_MARGIN
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = input,
        onValueChange = onInputChange,
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Off_White,
            unfocusedContainerColor = Off_White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = topPadding)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(
                    modifier = Modifier.clickable {
                        passwordVisible = !passwordVisible
                    },
                    imageVector = trailingIcon,
                    contentDescription = null
                )
            }
        }
    )
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
    modifier: Modifier,
    fontSize: TextUnit = 18.sp
) {
    Text(
        modifier = modifier,
        text = stringResource(id),
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Title(
    title: String,
    modifier: Modifier,
    fontSize: TextUnit = 18.sp
) {
    Text(
        modifier = modifier,
        text = title,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun UnderlineText(
    @StringRes id: Int,
    modifier: Modifier,
    fontSize: TextUnit = 14.sp,
    color: Color = Black
) {
    Text(
        modifier = modifier,
        text = stringResource(id),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            textDecoration = TextDecoration.Underline
        )
    )
}

@Composable
fun SubTitle(
    @StringRes id: Int,
    modifier: Modifier,
    fontSize: TextUnit = 14.sp,
    color: Color = Black
) {
    Text(
        modifier = modifier,
        text = stringResource(id),
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        color = color
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


@Composable
fun QtyButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Text(
        modifier = Modifier.clickable {
            onButtonClicked()
        },
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}

@Composable
fun SizeShape(
    modifier: Modifier,
    currentItem: ProductSize,
    onItemClicked: () -> Unit
) {

    if (currentItem.isPressed) {
        Text(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Black,
                    shape = RoundedCornerShape(CUSTOM_MARGIN)
                )
                .padding(vertical = 8.dp)
                .clickable {
                    onItemClicked()
                },
            text = currentItem.size,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            modifier = modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onItemClicked()
                },
            text = currentItem.size,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun BackButton(
    modifier: Modifier,
    onBackClicked: () -> Unit,
    isBackTextVisible: Boolean = true
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .clickable {
                onBackClicked()
            },
    ) {
        Image(
            painter = painterResource(R.drawable.outline_arrow_back),
            contentDescription = ""
        )

        if (isBackTextVisible) {
            Text(
                modifier = Modifier.padding(top = VERY_SMALL_MARGIN),
                text = "Back",
                fontSize = 14.sp,
            )
        }
    }
}


@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Black
        )
    }
}


@Composable
fun ErrorUi() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = Black,
            text = "Something went wrong!!",
        )
    }
}