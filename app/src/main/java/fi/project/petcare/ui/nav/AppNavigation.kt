package fi.project.petcare.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.ProfileScreen
import fi.project.petcare.ui.screens.WelcomeScreen
import fi.project.petcare.viewmodel.AuthViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
        //modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                vModel = authViewModel,
                onUserAuthenticated = { navController.navigate(Screen.Dashboard.Home.route) }
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