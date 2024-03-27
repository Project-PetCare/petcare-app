package fi.project.petcare.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.WelcomeScreen

@Composable
fun NavGraph(innerPadding: PaddingValues? = null, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
        //modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}