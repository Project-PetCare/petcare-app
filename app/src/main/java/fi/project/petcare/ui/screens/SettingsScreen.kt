package fi.project.petcare.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.project.petcare.ui.theme.PetCareTheme
import fi.project.petcare.viewmodel.AuthViewModel

data class SettingOption(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector? = null,
    var isSelected: Boolean = false,
    val subOptions: List<SettingOption>? = null,
    val onClick: (() -> Unit)? = null
)

val settingsList = listOf(
    SettingOption(
        title = "Account", subtitle = "Username, Email, Password, Security, Sign out", icon = Icons.Outlined.AccountCircle, subOptions = listOf(
            SettingOption(title = "Sign out", icon = Icons.AutoMirrored.Outlined.ArrowBack, onClick = { })
        )
    ),
    SettingOption(
        title = "Theme", subtitle = "Dynamic Colors, Dark Mode Preference", icon = Icons.Outlined.ColorLens, subOptions = listOf(
            SettingOption(title = "Dynamic Colors", subtitle = "Enable"),
        )
    ),
    SettingOption(
        title = "Language", subtitle = "English, Finnish, Swedish", icon = Icons.Outlined.Language, subOptions = listOf(
            SettingOption(title = "English", subtitle = "Selected"),
        )
    ),
    SettingOption(
        title = "About", subtitle = "Feedback, Report a bug, Version, Licenses", icon = Icons.AutoMirrored.Outlined.HelpOutline, subOptions = listOf(
            SettingOption(title = "Version", subtitle = "0.1.0"),
            SettingOption("Licenses", subtitle = "Apache 2.0")
        )
    )
)

