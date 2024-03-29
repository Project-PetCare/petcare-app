package fi.project.petcare.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.ProfileScreen
import fi.project.petcare.ui.screens.WelcomeScreen

/**
 * Contract for information needed on every PetCare navigation destination
 */
sealed class Screen(val route: String, val unselectedIcon: ImageVector? = null, val selectedIcon: ImageVector? = null) {

    object Welcome : Screen(route = "Welcome")
    object Home : Screen(route = "Home", unselectedIcon = Icons.Outlined.Home, selectedIcon = Icons.Filled.Home)
    object PetProfile : Screen(route = "Profiles", unselectedIcon = Icons.Outlined.Lock, selectedIcon = Icons.Filled.Lock)
}

// Screens to be displayed in bottom nav bar
val petCareDestinations = listOf(
    Screen.Home,
    Screen.PetProfile
)
