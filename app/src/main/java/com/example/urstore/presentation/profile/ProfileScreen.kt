package com.example.urstore.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.ui.theme.Lighter_Beige
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.AlertDialog
import com.example.urstore.util.BackButton
import com.example.urstore.util.SettingItem
import com.example.urstore.util.SettingOneItem
import com.example.urstore.util.SubTitle
import com.example.urstore.util.Title
import com.example.urstore.util.UiEffect


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is UiEffect.PobBackStack -> navController.popBackStack()
                is UiEffect.Navigate -> navController.navigate(effect.route)
                is UiEffect.ClearBackStack -> {
                    navController.navigate(effect.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }

                else -> Unit
            }
        }
    }


    Column(
        modifier = Modifier
            .background(Lighter_Beige)
            .fillMaxSize()
            .padding(top = LARGE_MARGIN)
    ) {
        ProfileHeader(viewModel)

        SettingItem(
            title = stringResource(R.string.edit_profile),
            secondTitle = stringResource(R.string.change_password),
            settingName = stringResource(R.string.account),
            leadingIcon = Icons.Outlined.Person,
            secondLeadingIcon = Icons.Outlined.Lock,
            firstCaption = "Update your personal information",
            secondCaption = "Update your password",
            onFirstItemClicked = {
                viewModel.onIntent(ProfileIntent.EditProfile)
            },
            onSecondItemClicked = {
                viewModel.onIntent(ProfileIntent.ChangePassword)
            }
        )

        SettingOneItem(
            title = stringResource(R.string.push_notification),
            settingName = stringResource(R.string.notification),
            leadingIcon = Icons.Outlined.Notifications,
            caption = "Receive updates and alerts"
        )

        Spacer(modifier = Modifier.height(MEDIUM_MARGIN))

        SettingItem(
            title = stringResource(R.string.help_support),
            secondTitle = uiState.authName,
            settingName = stringResource(R.string.more),
            leadingIcon = Icons.AutoMirrored.Outlined.Help,
            secondLeadingIcon = Icons.Outlined.PowerSettingsNew,
            firstCaption = "Get help or contact support",
            secondCaption = uiState.authCaption,
            onFirstItemClicked = {},
            onSecondItemClicked = {
                if (uiState.isUserLoggedIn) {
                    viewModel.onIntent(ProfileIntent.ShowDialog(true))
                } else {
                    viewModel.onIntent(ProfileIntent.Login)
                }
            }
        )


        AlertDialog(
            isVisible = uiState.isLoginDialogActive,
            title = if (uiState.isUserLoggedIn) stringResource(R.string.want_to_logout)
            else stringResource(R.string.login_to_edit_user),
            confirmTitle = if (uiState.isUserLoggedIn) stringResource(R.string.logout)
            else stringResource(R.string.login),
            icon = if (uiState.isUserLoggedIn) Icons.Outlined.Logout
            else Icons.Outlined.Login,
            dismissTitle = stringResource(R.string.cancel),
            description = stringResource(R.string.returned_to_login),
            onDismiss = {
                viewModel.onIntent(ProfileIntent.ShowDialog(false))
            },
            onConfirm = {
                viewModel.apply {
                    onIntent(ProfileIntent.ShowDialog(false))
                    if (uiState.isUserLoggedIn) onIntent(ProfileIntent.Logout)
                    else onIntent(ProfileIntent.Login)
                }
            }
        )
    }
}


@Composable
fun ProfileHeader(viewModel: ProfileViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (backBtn, titleText, captionText, icon) = createRefs()

        BackButton(
            modifier = Modifier.constrainAs(backBtn) {
                start.linkTo(parent.start, MEDIUM_MARGIN)
                top.linkTo(parent.top, MEDIUM_MARGIN)
            },
            onBackClicked = {
                viewModel.onIntent(ProfileIntent.GoBack)
            },
            isBackTextVisible = false
        )


        Title(
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(backBtn.bottom, CUSTOM_MARGIN)
                start.linkTo(backBtn.start)
            },
            id = R.string.settings,
            fontSize = 29.sp
        )

        SubTitle(
            modifier = Modifier.constrainAs(captionText) {
                top.linkTo(titleText.bottom, MEDIUM_MARGIN)
                start.linkTo(titleText.start)
            },
            id = R.string.settings_caption,
            color = Color.Black.copy(alpha = 0.6f),
            fontSize = 12.sp
        )


        Image(
            painter = painterResource(R.drawable.ic_setting),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(icon) {
                    start.linkTo(captionText.end)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .size(170.dp)
        )
    }
}