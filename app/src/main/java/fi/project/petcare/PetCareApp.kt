package fi.project.petcare

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import fi.project.petcare.ui.nav.NavGraph
import fi.project.petcare.ui.theme.PetCareTheme
import fi.project.petcare.viewmodel.AuthUiState
import fi.project.petcare.viewmodel.AuthViewModel

@Composable
fun PetCareApp() {
    val authViewModel: AuthViewModel = viewModel()
    val navController = rememberNavController()

    var dynamicColors = false
    // Raw implementation of dynamic colors set by user options
//    if (authState is AuthUiState.Authenticated) {
//        dynamicColors = true
//    }

    PetCareTheme (
        dynamicColor = dynamicColors
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
<<<<<<< HEAD
//           WelcomeScreen()


            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                // Define navigation routes
                composable("home") {
                    HomeScreen(navController = navController)
                }
                composable("profile") {
                    ProfileScreen(petName = "Fluffy", navController = navController)
                }
            }



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

//           WelcomeScreen()


            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                // Define navigation routes
                composable("home") {
                    HomeScreen(navController = navController)
                }


            }
        }
    }
}
=======
            NavGraph(
                userViewModel = authViewModel,
                navController = navController
            )
        }
    }
}
>>>>>>> main
