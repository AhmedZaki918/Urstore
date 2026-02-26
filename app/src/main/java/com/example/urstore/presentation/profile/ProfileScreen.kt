package com.example.urstore.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.urstore.R
import com.example.urstore.presentation.navigation.Screen
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.EXTRA_LARGE_MARGIN
import com.example.urstore.ui.theme.MEDIUM_MARGIN
import com.example.urstore.util.AlertDialog
import com.example.urstore.util.BackButton
import com.example.urstore.util.SettingItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            SettingsHeader {
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.height(CUSTOM_MARGIN))
            SettingsContent(
                viewModel,
                navController,
                uiState
            )
        }
    }
}

@Composable
fun SettingsContent(
    viewModel: ProfileViewModel,
    navController: NavHostController,
    uiState: ProfileUiState
) {

    SettingItem(
        title = stringResource(R.string.edit_profile),
        secondTitle = stringResource(R.string.change_password),
        settingName = stringResource(R.string.account),
        leadingIcon = Icons.Outlined.Person,
        secondLeadingIcon = Icons.Outlined.Lock,
    )

    SettingItem(
        title = stringResource(R.string.push_notification),
        settingName = stringResource(R.string.notification),
        leadingIcon = Icons.Outlined.Notifications,
        isToggleButtonExist = true,
        onItemClicked = {}
    )

    SettingItem(
        title = stringResource(R.string.help_support),
        leadingIcon = Icons.AutoMirrored.Outlined.Help,
        settingName = stringResource(R.string.more),
        onItemClicked = {}
    )

    SettingItem(
        title = stringResource(R.string.log_out),
        leadingIcon = Icons.Outlined.PowerSettingsNew,
        isArrowExist = false,
        isSettingNameExist = false,
        onItemClicked = {
            viewModel.onIntent(
                ProfileIntent.ShowDialog(true)
            )
        }
    )


    AlertDialog(
        isVisible = uiState.isLoginDialogActive,
        title = stringResource(R.string.want_to_logout),
        description = stringResource(R.string.returned_to_login),
        confirmTitle = stringResource(R.string.logout),
        dismissTitle = stringResource(R.string.cancel),
        icon = Icons.Outlined.Logout,
        onDismiss = {
            viewModel.onIntent(ProfileIntent.ShowDialog(false))
        },
        onConfirm = {
            viewModel.apply {
                onIntent(ProfileIntent.ShowDialog(false))
                onIntent(ProfileIntent.Logout)
            }
            navController.navigate(Screen.LOGIN_SCREEN.route)
        }
    )
}

@Composable
fun SettingsHeader(
    onBackClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = EXTRA_LARGE_MARGIN)
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
            text = stringResource(R.string.settings),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}