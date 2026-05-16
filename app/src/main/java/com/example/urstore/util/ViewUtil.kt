package com.example.urstore.util

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.scale
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.urstore.R
import com.example.urstore.data.model.drinks.ProductSize
import com.example.urstore.ui.theme.Black
import com.example.urstore.ui.theme.Brown
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.Dark_Yellow
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Light_Brown
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.ui.theme.Medium_Brown
import com.example.urstore.ui.theme.Off_White
import com.example.urstore.ui.theme.SMALL_MARGIN
import com.example.urstore.ui.theme.VERY_SMALL_MARGIN
import com.example.urstore.ui.theme.White


@Composable
fun ButtonShopApp(
    modifier: Modifier = Modifier.wrapContentWidth(),
    label: String,
    isVisible: Boolean = true,
    isButtonClickable: Boolean = true,
    onButtonClicked: () -> Unit
) {
    if (isVisible) {
        Button(
            onClick = {
                if (isButtonClickable) {
                    onButtonClicked()
                }
            },
            shape = RoundedCornerShape(LARGE_MARGIN),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isButtonClickable) Brown else Brown.copy(alpha = 0.7f),
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
    isVisible: Boolean = true,
    modifier: Modifier,
    onClicked: () -> Unit,
    text: String,
    containerColor: Color = White,
    contentColor: Color = Black,
    floatingActionSize: Dp = 30.dp,
    textFontSize: TextUnit = 18.sp,
    floatingDefaultElevation: Dp = 8.dp,
) {
    if (isVisible) {
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
}


@Composable
fun TextFieldShopApp(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType,
    topPadding: Dp = SMALL_MARGIN
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
        modifier = modifier
            .fillMaxWidth()
            .padding(start = CUSTOM_MARGIN, end = CUSTOM_MARGIN, top = topPadding)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun TextFieldShopApp(
    modifier: Modifier = Modifier,
    input: String,
    onInputChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector? = null,
    keyboardType: KeyboardType,
    topPadding: Dp = SMALL_MARGIN
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
        modifier = modifier
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
    color: Color = Black,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier,
        text = stringResource(id),
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        style = style
    )
}


@Composable
fun SubTitle(
    title: String,
    modifier: Modifier,
    fontSize: TextUnit = 14.sp,
    color: Color = Black,
    fontWeight: FontWeight? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier,
        text = title,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        style = style
    )
}

@Composable
fun QtyButton(
    isVisible: Boolean = true,
    text: String,
    onButtonClicked: () -> Unit
) {
    if (isVisible) {
        Text(
            modifier = Modifier.clickable {
                onButtonClicked()
            },
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
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
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth(0.2f)
            .height(40.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
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
fun LinearLoadingIndicator(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
) {
    if (isVisible) {
        Box(
            modifier = modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                color = Brown,
                modifier = Modifier.fillMaxWidth(0.25f)
            )
        }
    }
}

@Composable
fun CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 25.dp,
    isVisible: Boolean = true,
    color: Color
) {
    if (isVisible) {
        Box(
            modifier = modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = color,
                modifier = Modifier.size(size)
            )
        }
    }
}


@Composable
fun SnackBar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
            .padding(MEDIUM_MARGIN)
    ) { data ->
        Snackbar(
            containerColor = Off_White,
            contentColor = Medium_Brown,
            action = {
                IconButton(onClick = {
                    data.dismiss()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = Brown
                    )
                }
            }
        ) {
            // Show task has been completed successfully
            if (data.visuals.actionLabel == ActionLabel.SUCCESS.value) {
                Row(modifier = Modifier.wrapContentWidth()) {
                    CircleWithIcon(
                        circleColor = Brown,
                        icon = Icons.Default.Done,
                        iconTint = Color.White
                    )

                    Text(text = data.visuals.message)
                }

            } else {
                // Show task has been failed
                ConstraintLayout(modifier = Modifier.wrapContentSize()) {
                    val (icon, text) = createRefs()

                    Icon(
                        modifier = Modifier
                            .constrainAs(icon) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(start = SMALL_MARGIN, end = SMALL_MARGIN),
                        imageVector = Icons.Default.Error,
                        contentDescription = "",
                        tint = Brown
                    )

                    Text(
                        modifier = Modifier.constrainAs(text) {
                            start.linkTo(icon.end)
                            top.linkTo(icon.top)
                            bottom.linkTo(icon.bottom)
                        },
                        text = data.visuals.message
                    )
                }
            }
        }
    }
}


@Composable
fun CircleWithIcon(
    circleColor: Color,
    icon: ImageVector,
    iconTint: Color
) {
    Box(
        modifier = Modifier
            .padding(end = SMALL_MARGIN)
            .wrapContentSize()
            .background(color = circleColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(23.dp)
                .padding(VERY_SMALL_MARGIN),
            imageVector = icon,
            contentDescription = "",
            tint = iconTint
        )
    }
}


@Composable
fun ErrorUi(
    modifier: Modifier,
    isErrorIconVisible: Boolean = true,
    retry: () -> Unit = {},
    topPadding: Dp = 32.dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .padding(top = topPadding)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                retry.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.wrapContentHeight()) {

            if (isErrorIconVisible) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = "Error ui icon"
                )
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                color = Black,
                text = "Something went wrong!!",
            )

            UnderlineText(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = SMALL_MARGIN),
                id = R.string.try_again
            )
        }
    }
}


@Composable
fun SettingItem(
    title: String,
    leadingIcon: ImageVector,
    secondTitle: String,
    secondLeadingIcon: ImageVector,
    settingName: String,
    firstCaption: String = "",
    secondCaption: String = "",
    onFirstItemClicked: () -> Unit = {},
    onSecondItemClicked: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            modifier = Modifier.padding(start = CUSTOM_MARGIN),
            text = settingName,
            color = Black,
            fontSize = 12.sp
        )


        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(MEDIUM_MARGIN),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = MEDIUM_MARGIN,
                    end = MEDIUM_MARGIN,
                    top = SMALL_MARGIN
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (iconImage, arrowImage, lineDivider, secondIconImage, secondArrowImage,
                    accountProfileColumn, moreColumn) = createRefs()

                Icon(
                    modifier = Modifier.constrainAs(iconImage) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(parent.top, MEDIUM_MARGIN)
                    },
                    imageVector = leadingIcon,
                    contentDescription = ""
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onFirstItemClicked.invoke()
                        }
                        .constrainAs(accountProfileColumn) {
                            start.linkTo(iconImage.end, MEDIUM_MARGIN)
                            top.linkTo(iconImage.top)
                        }) {

                    Text(
                        text = title,
                        color = Color.Black
                    )

                    Text(
                        text = firstCaption,
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }



                Icon(
                    modifier = Modifier
                        .constrainAs(arrowImage) {
                            end.linkTo(parent.end, MEDIUM_MARGIN)
                            top.linkTo(accountProfileColumn.top)
                            bottom.linkTo(accountProfileColumn.bottom)
                        }
                        .size(16.dp),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = "",
                    tint = Color.LightGray
                )



                HorizontalDivider(
                    modifier = Modifier
                        .constrainAs(lineDivider) {
                            top.linkTo(accountProfileColumn.bottom)
                            start.linkTo(accountProfileColumn.start)
                        }
                        .padding(top = MEDIUM_MARGIN, bottom = MEDIUM_MARGIN),
                    color = Color.Gray.copy(alpha = 0.2f)
                )


                Icon(
                    modifier = Modifier.constrainAs(secondIconImage) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(lineDivider.bottom)
                    },
                    imageVector = secondLeadingIcon,
                    contentDescription = ""
                )



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onSecondItemClicked.invoke()
                        }
                        .constrainAs(moreColumn) {
                            start.linkTo(secondIconImage.end, MEDIUM_MARGIN)
                            top.linkTo(secondIconImage.top)
                        }) {

                    Text(
                        text = secondTitle,
                        color = Color.Black
                    )

                    Text(
                        modifier = Modifier.padding(bottom = MEDIUM_MARGIN),
                        text = secondCaption,
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                }


                Icon(
                    modifier = Modifier
                        .constrainAs(secondArrowImage) {
                            end.linkTo(parent.end, MEDIUM_MARGIN)
                            top.linkTo(moreColumn.top)
                            bottom.linkTo(moreColumn.bottom)
                        }
                        .size(16.dp),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = "",
                    tint = Color.LightGray
                )
            }
        }
    }
}


@Composable
fun SettingOneItem(
    title: String,
    leadingIcon: ImageVector,
    settingName: String = "",
    caption: String = ""
) {
    var checked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.wrapContentSize()
    ) {

        Text(
            modifier = Modifier.padding(start = MEDIUM_MARGIN, top = MEDIUM_MARGIN),
            text = settingName,
            color = Black,
            fontSize = 12.sp
        )


        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    start = MEDIUM_MARGIN,
                    end = MEDIUM_MARGIN,
                    top = SMALL_MARGIN
                ),
            shape = RoundedCornerShape(MEDIUM_MARGIN),
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (iconImage, titleText, toggleButton, captionText) = createRefs()

                Icon(
                    modifier = Modifier.constrainAs(iconImage) {
                        start.linkTo(parent.start, MEDIUM_MARGIN)
                        top.linkTo(parent.top, MEDIUM_MARGIN)
                    },
                    imageVector = leadingIcon,
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier.constrainAs(titleText) {
                        start.linkTo(iconImage.end, MEDIUM_MARGIN)
                        top.linkTo(iconImage.top)
                    },
                    text = title,
                )


                Text(
                    modifier = Modifier
                        .constrainAs(captionText) {
                            start.linkTo(titleText.start)
                            top.linkTo(titleText.bottom)
                        }
                        .padding(bottom = MEDIUM_MARGIN),
                    text = caption,
                    color = Color.Gray,
                    fontSize = 11.sp
                )



                Switch(
                    modifier = Modifier
                        .constrainAs(toggleButton) {
                            end.linkTo(parent.end, MEDIUM_MARGIN)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .scale(0.7f),
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Brown,
                    ),
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }
                )
            }
        }
    }
}


@Composable
fun AlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    isVisible: Boolean,
    title: String,
    description: String,
    confirmTitle: String,
    dismissTitle: String,
    icon: ImageVector
) {
    if (isVisible) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Off_White
                ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = ""
                    )

                    Spacer(modifier = Modifier.height(MEDIUM_MARGIN))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(SMALL_MARGIN))

                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.5f)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MEDIUM_MARGIN)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(dismissTitle)
                        }

                        Spacer(modifier = Modifier.width(SMALL_MARGIN))

                        Button(
                            onClick = onConfirm,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Brown
                            )
                        ) {
                            Text(confirmTitle)
                        }
                    }
                }
            }
        }
    }
}