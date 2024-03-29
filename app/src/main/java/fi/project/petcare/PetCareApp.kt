package fi.project.petcare

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fi.project.petcare.ui.screens.HomeScreen
import fi.project.petcare.ui.screens.ProfileScreen
import fi.project.petcare.ui.screens.WelcomeScreen
import fi.project.petcare.ui.theme.PetCareTheme

@Composable
fun PetCareApp() {
    PetCareTheme (
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
           WelcomeScreen()
<<<<<<< Updated upstream
            // Create a NavHost home to profile page
=======
//             Create a NavHost home to profile page
>>>>>>> Stashed changes
//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = "home") {
//                // Define navigation routes
//                composable("home") {
//                    HomeScreen(navController = navController)
//                }
//                composable("profile") {
//                    ProfileScreen(petName = "Fluffy", navController = navController)
//                }
//            }
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        }
    }
}

@Preview
@Composable
fun PetCareAppPreview() {
    PetCareTheme (
        darkTheme = true,
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
<<<<<<< Updated upstream
           WelcomeScreen()
=======
         WelcomeScreen()
>>>>>>> Stashed changes

//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = "home") {
//                // Define navigation routes
//                composable("home") {
//                    HomeScreen(navController = navController)
//                }
<<<<<<< Updated upstream
=======
//
//
>>>>>>> Stashed changes
//            }
        }
    }
}