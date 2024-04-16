package fi.project.petcare.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import fi.project.petcare.ui.nav.petCareDestinations

@Composable
fun PetCareBottomBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        petCareDestinations.forEach { screen ->
            NavigationBarItem(
                icon = {
                    if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) Icon(screen.selectedIcon!!, contentDescription = null)
                    else Icon(screen.unselectedIcon!!, contentDescription = null)
                },
                label = { Text(text = screen.route) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}