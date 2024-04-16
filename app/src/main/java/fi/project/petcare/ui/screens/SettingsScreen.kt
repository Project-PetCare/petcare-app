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
import androidx.compose.ui.unit.dp

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

@Composable
fun SettingSubItem(
    subOptions: List<SettingOption>?,
    isSelectedState: MutableState<Boolean>,
    onSignOut: () -> Unit
) {
    Surface(
        tonalElevation = if (isSelectedState.value) 3.dp else 0.dp,
        shadowElevation = if (isSelectedState.value) 3.dp else 0.dp,
        modifier = Modifier
            .clickable {
                when (subOptions?.get(0)?.title) {
                    "Sign out" -> {
                        onSignOut()
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            subOptions?.forEach { subOption ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = subOption.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = subOption.subtitle ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


@Composable
fun SettingItem(
    setting: SettingOption,
    onSignOut: () -> Unit
) {
    val isSelectedState = remember { mutableStateOf(setting.isSelected) }
    Surface(
        shadowElevation = if (isSelectedState.value) 6.dp else 0.dp,
        modifier = Modifier
            .clickable {
                isSelectedState.value = !isSelectedState.value
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            setting.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = setting.title,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Column {
                Text(
                    text = setting.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                if (!isSelectedState.value) {
                    Text(
                        text = setting.subtitle ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
    if (isSelectedState.value) {
        SettingSubItem(setting.subOptions, isSelectedState, onSignOut)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onSignOut: () -> Unit,
    settingsOptions: List<SettingOption>? = settingsList
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        maxLines = 1,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            settingsOptions?.forEach { option ->
                SettingItem(setting = option, onSignOut = onSignOut)
            }
        }
    }
}