package fi.project.petcare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import fi.project.petcare.ui.composables.LoadingIndicator
import fi.project.petcare.ui.nav.NavGraph
import fi.project.petcare.ui.screens.WelcomeScreen
import fi.project.petcare.ui.theme.PetCareTheme
import fi.project.petcare.viewmodel.AuthUiState
import fi.project.petcare.viewmodel.AuthViewModel

@Composable
fun PetCareApp() {
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authUiState.collectAsState()
    val navController = rememberNavController()

    val dynamicColors = false
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
            when (authState) {
                is AuthUiState.Unauthenticated -> {
                    WelcomeScreen(vModel = authViewModel)
                }
                is AuthUiState.Loading -> {
                    Scaffold { innerPadding ->
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            LoadingIndicator(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                is AuthUiState.Authenticated -> {
                    // Dashboard
                    NavGraph(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
                is AuthUiState.Error -> {
                    // ErrorScreen
                    Scaffold { innerPadding ->
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "Error: ${(authState as AuthUiState.Error).message}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}