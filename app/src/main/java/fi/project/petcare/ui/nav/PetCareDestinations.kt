package fi.project.petcare.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Contract for information needed on every PetCare navigation destination
 */
sealed class Screen(val route: String, val unselectedIcon: ImageVector? = null, val selectedIcon: ImageVector? = null) {

    object Welcome : Screen(route = "Welcome")
    object Dashboard : Screen(route = "Dashboard") {
        object Home : Screen(route = "Home", unselectedIcon = Icons.Outlined.Home, selectedIcon = Icons.Filled.Home)
        object Pets : Screen(route = "Pets", unselectedIcon = Icons.Outlined.Pets, selectedIcon = Icons.Filled.Pets)
        object Community : Screen(route = "Community", unselectedIcon = Icons.Outlined.Groups, selectedIcon = Icons.Filled.Groups)
    }
    object PetProfile : Screen(route = "PetProfile", unselectedIcon = Icons.Outlined.Pets)
    object Settings : Screen(route = "Settings", unselectedIcon = Icons.Outlined.Settings, selectedIcon = Icons.Filled.Settings)
}

// Screens to be displayed in bottom nav bar
val petCareDestinations = listOf(
    Screen.Dashboard.Home,
    Screen.Dashboard.Pets,
    Screen.Dashboard.Community,
)
