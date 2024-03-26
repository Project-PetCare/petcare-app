package fi.project.petcare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.project.petcare.R
import fi.project.petcare.ui.composables.GoogleSignInButton
import fi.project.petcare.ui.composables.Login
import fi.project.petcare.ui.composables.Register
import fi.project.petcare.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(vModel: AuthViewModel = viewModel()) {
    var openDialog by remember { mutableStateOf(false) }
    fun toggleDialog() { openDialog = !openDialog }
    val showBottomSheet by vModel.showBottomSheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(94.dp)
        ) {
            Text(
                text = "Welcome to PetCare!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp)
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
                        onClick = { vModel.openLoginSheet() },
                        modifier = Modifier.size(width = 160.dp, height = 58.dp)
                    ) {
                        Text(text = "Sign in")
                    }
                    Button(
                        onClick = { vModel.openRegisterSheet() },
                        modifier = Modifier.size(width = 160.dp, height = 58.dp)
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
                    GoogleSignInButton(vModel = vModel)
                    OutlinedButton(
                        onClick = { /*TODO*/}, // Sign up with passkey,
                        modifier = Modifier.size(width = 160.dp, height = 58.dp)
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

    if (showBottomSheet) {
        val authState by vModel.authMode.collectAsState()
        ModalBottomSheet(
            onDismissRequest = {
                vModel.closeBottomSheet()
            },
            sheetState = bottomSheetState,
            content = {
                if (authState == AuthViewModel.AuthMode.REGISTER) {
                    Register(scope = scope, sheetState = bottomSheetState, onRegister = vModel::signUp)
                } else {
                    Login(scope = scope, sheetState = bottomSheetState, onLogin = vModel::signIn)
                }
            }
        )
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { /* do nothing */ },
            icon = { Icon(Icons.Filled.Handshake, contentDescription = null) },
            title = {
                Text(text = "User Agreement")
            },
            text = {
                Text(
                    text = "1. PetCare is for personal use only.\n" +
                            "\n 2. We respect your privacy and won't share your data.\n" +
                            "\n 3. You're responsible for your pet's info accuracy.\n" +
                            "\n 4. Consult a vet for medical advice.\n" +
                            "\n 5. Expect updates to improve PetCare.\n" +
                            "\n Questions? Contact us at [contact@email.com].\n",
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        toggleDialog()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        toggleDialog()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}