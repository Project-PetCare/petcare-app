package fi.project.petcare.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fi.project.petcare.ui.composables.Dashboard
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.ProfileScreen
import fi.project.petcare.ui.screens.SettingsScreen
import fi.project.petcare.ui.screens.WelcomeScreen
import fi.project.petcare.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    userViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                vModel = userViewModel,
                onUserAuthenticated = { navController.navigate(Screen.Dashboard.Home.route) }
            )
        }
        navigation(
            route = Screen.Dashboard.route,
            startDestination = Screen.Dashboard.Home.route
        ) {
            composable(Screen.Dashboard.Home.route) {
                Dashboard(
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    navController = navController
                ) {
                    HomeScreen(
                        onNavigateToProfile = { navController.navigate(Screen.Dashboard.PetProfile.route) }
                    )
                }
            }
            composable(Screen.Dashboard.PetProfile.route) {
                Dashboard(
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    navController = navController
                ) {
                    ProfileScreen(petName = "Fluffy", navController = navController)
                }
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onSignOut = {
                        userViewModel.signOut()
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}