package fi.project.petcare.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Contract for information needed on every PetCare navigation destination
 */
sealed class Screen(val route: String, val unselectedIcon: ImageVector? = null, val selectedIcon: ImageVector? = null) {

    object Welcome : Screen(route = "welcome")
    object Dashboard : Screen(route = "dashboard") {
        object Home : Screen(route = "home", unselectedIcon = Icons.Outlined.Home, selectedIcon = Icons.Filled.Home)
        object PetProfile : Screen(route = "profile", unselectedIcon = Icons.Outlined.Lock, selectedIcon = Icons.Filled.Lock)
    }
    object Settings : Screen(route = "settings", unselectedIcon = Icons.Outlined.Settings, selectedIcon = Icons.Filled.Settings)
}

// Screens to be displayed in bottom nav bar
val petCareDestinations = listOf(
    Screen.Dashboard.Home,
    Screen.Dashboard.PetProfile
)
