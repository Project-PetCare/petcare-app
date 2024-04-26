package fi.project.petcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fi.project.petcare.R
import fi.project.petcare.ui.composables.GoogleSignInButton
import fi.project.petcare.ui.composables.Login
import fi.project.petcare.ui.composables.Register
import fi.project.petcare.ui.theme.bg_gr
import fi.project.petcare.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    vModel: AuthViewModel,
    snackbarHostState: SnackbarHostState
) {
    var showSheet by remember { mutableStateOf(false) }
    val toggleShowSheet = { showSheet = !showSheet }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isLogin by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val brush = Brush.radialGradient(
        listOf(bg_gr, MaterialTheme.colorScheme.background),
    )

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                toggleShowSheet()
            },
            sheetState = bottomSheetState,
            content = {
                if (!isLogin) {
                    Register(
                        scope = scope,
                        bottomSheetState = bottomSheetState,
                        toggleShowSheet = toggleShowSheet,
                        onRegister = vModel::onSignUp
                    )
                } else {
                    Login(
                        scope = scope,
                        bottomSheetState = bottomSheetState,
                        toggleShowSheet = toggleShowSheet,
                        onLogin = vModel::onSignIn
                    )
                }
            }
        )
    }

    Scaffold { innerPadding ->
        Box (
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Welcome to PetCare",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "PetCare is here to help you keep track of your pets' health and activities.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .background(brush)
                        .paint(
                            painterResource(id = R.drawable.ic_cat_welcome)
                        )
                ) {
                    SnackbarHost(
                        hostState = snackbarHostState
                    )
                }
                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Button(
                            onClick = { isLogin = true; toggleShowSheet() },
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        ) {
                            Text(text = "Sign in")
                        }
                        Button(
                            onClick = { isLogin = false; toggleShowSheet() },
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        ) {
                            Text(text = "Join PetCare")
                        }
                    }
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        GoogleSignInButton(
                            client = vModel.client,
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        )
                        OutlinedButton(
                            onClick = { vModel.onDemoSignIn() },
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        ) {
                            Text(text = "Demo Mode")
                        }
                    }
                }
            }
        }
    }
}
