package com.example.urstore.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urstore.ui.theme.Beige
import com.example.urstore.ui.theme.CUSTOM_MARGIN
import com.example.urstore.ui.theme.LARGE_MARGIN
import com.example.urstore.util.SettingItem

@Composable
fun ProfileScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            SettingsHeader()
            Spacer(modifier = Modifier.height(CUSTOM_MARGIN))
            SettingsContent()
        }
    }
}

@Composable
fun SettingsContent() {
    SettingItem(
        title = "Edit Profile",
        leadingIcon = Icons.Outlined.Person,
        secondTitle = "Change Password",
        secondLeadingIcon = Icons.Outlined.Lock,
        settingName = "ACCOUNT"
    )

    SettingItem(
        title = "Push Notification",
        leadingIcon = Icons.Outlined.Notifications,
        settingName = "NOTIFICATION",
        isToggleButtonExist = true
    )

    SettingItem(
        title = "Help & Support",
        leadingIcon = Icons.AutoMirrored.Outlined.Help,
        settingName = "More"
    )

    SettingItem(
        title = "Log Out",
        leadingIcon = Icons.Outlined.PowerSettingsNew,
        isArrowExist = false,
        isSettingNameExist = false
    )
}

@Composable
fun SettingsHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 45.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Settings",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}