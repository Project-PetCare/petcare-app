package fi.project.petcare.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.ProfileScreen
import fi.project.petcare.ui.screens.WelcomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
        //modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onSignUpComplete = { navController.navigate(Screen.Dashboard.Home.route) }
            )
        }
        navigation(
            route = Screen.Dashboard.route,
            startDestination = Screen.Dashboard.Home.route
        ) {
            composable(Screen.Dashboard.Home.route) {
                HomeScreen(
                    onNavigateToProfile = { navController.navigate(Screen.Dashboard.PetProfile.route) },
                    navController = navController
                )
            }
            composable(Screen.Dashboard.PetProfile.route) {
                ProfileScreen(petName = "Fluffy", navController = navController)
            }
        }
    }
}