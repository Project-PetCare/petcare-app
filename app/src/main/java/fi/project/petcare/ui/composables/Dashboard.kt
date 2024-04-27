package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    onNavigateToSettings: () -> Unit,
    onAddPetClick: () -> Unit? = {},
    navController: NavController,
    content: @Composable () -> Unit
) {
    val scrollTopBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollTopBarBehavior.nestedScrollConnection),
        topBar = {
            PetCareTopBar(
                navController = navController,
                onSettingsClick = { onNavigateToSettings() },
                onAddClick = { onAddPetClick() },
                scrollBehavior = scrollTopBarBehavior
            )
        },
        bottomBar = {
            PetCareBottomBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }
}
