package fi.project.petcare.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fi.project.petcare.model.data.User
import fi.project.petcare.ui.composables.Dashboard
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.PetListScreen
import fi.project.petcare.ui.screens.ProfileScreen
import fi.project.petcare.ui.screens.SettingsScreen
import fi.project.petcare.viewmodel.AuthUiState
import fi.project.petcare.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val userState by authViewModel.authUiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.Home.route
    ) {
        composable(Screen.Dashboard.Home.route) {
            Dashboard(
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                navController = navController
            ) {
                val user =
                    if (userState is AuthUiState.Authenticated)
                        (userState as AuthUiState.Authenticated).user
                    else User(id = "demo-user", name = "John Doe", email = "johndoe@email.com")
                HomeScreen(
                    user = user
                )
            }
        }
        composable(Screen.Dashboard.Pets.route) {
            Dashboard(
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                navController = navController
            ) {
                PetListScreen(
                    onNavigateToProfile = { navController.navigate(Screen.PetProfile.route) }
                )
            }
        }
        composable(Screen.PetProfile.route) {
            ProfileScreen(petName = "Fluffy", navController = navController)
        }
        composable(Screen.Dashboard.Community.route) {
            Dashboard(
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                navController = navController
            ) {
                Text(
                    text = "Community content"
                )
            }
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onSignOut = {
                    authViewModel.onSignOut()
                }
            )
        }
    }
}