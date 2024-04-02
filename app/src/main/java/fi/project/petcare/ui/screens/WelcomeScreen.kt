package fi.project.petcare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fi.project.petcare.R
import fi.project.petcare.ui.composables.GoogleSignInButton
import fi.project.petcare.ui.composables.Login
import fi.project.petcare.ui.composables.Register
import fi.project.petcare.viewmodel.AuthUiState
import fi.project.petcare.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(vModel: AuthViewModel, onUserAuthenticated: () -> Unit) {
    var showSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isLogin by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val authState by vModel.authUiState.collectAsState()

    Scaffold(
        modifier = Modifier.padding(24.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column (
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Welcome to PetCare!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                isLogin = true
                                showSheet = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        ) {
                            Text(text = "Sign in")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                isLogin = false
                                showSheet = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        ) {
                            Text(text = "Join PetCare")
                        }
                    }
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        GoogleSignInButton(
                            onClick = vModel::googleSignIn,
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        OutlinedButton(
                            onClick = { onUserAuthenticated() },
                            modifier = Modifier
                                .weight(1f)
                                .height(58.dp)
                        ) {
                            Text(text = "Passkey ")
                            Icon( painterResource(id = R.drawable.ic_passkey), contentDescription = "Passkey")
                        }
                    }
                    Column (
                        modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "This footer may not be necessary here.",
                            maxLines = 2,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showSheet = false
                },
                sheetState = bottomSheetState,
                content = {
                    if (!isLogin) {
                        Register(
                            authState = authState,
                            onRegister = vModel::signUp
                        )
                    } else {
                        Login(
                            authState = authState,
                            onLogin = vModel::signIn
                        )
                    }
                }
            )
        }

        if (authState == AuthUiState.Authenticated) {
            LaunchedEffect(key1 = Unit) {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showSheet = false
                        onUserAuthenticated()
                    }
                }
            }
        }

    }
}
