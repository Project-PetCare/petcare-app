package fi.project.petcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.project.petcare.ui.composables.Register
import fi.project.petcare.viewmodel.PetCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(vModel: PetCareViewModel = viewModel()) {
    val showBottomSheet by vModel.showBottomSheet.collectAsState()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    Text(
        text = "Welcome to",
        overflow = TextOverflow.Clip,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(24.dp)
    )
    Text(
        text = "PetCare!",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(24.dp)
    )
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(width = 160.dp, height = 48.dp)
                ) {
                    Text(text = "Login")
                }
                Button(
                    onClick = { vModel.openRegistrationForm() },
                    modifier = Modifier.size(width = 160.dp, height = 48.dp)
                ) {
                    Text(text = "Sign up")
                }
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = "or use")
                OutlinedButton(
                    onClick = { /*TODO*/ }, //Login with Google,
                ) {
//                    ProviderIcon(Google)
                    Text("G")
                }
                OutlinedButton(
                    onClick = { /*TODO*/ }, //Login with Google,
                ) {
//                    ProviderIcon(Google)
                    Text("A")
                }
                OutlinedButton(
                    onClick = { /*TODO*/}, //Login with Google,
                ) {
//                    ProviderIcon(Google)
                    Icon( Icons.Outlined.Lock, contentDescription = "Passkey")
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            dragHandle = { Spacer(modifier = Modifier.width(750.dp).height(16.dp).background(Color.Blue)) },
            onDismissRequest = {
                vModel.closeRegistrationForm()
            },
            sheetState = bottomSheetState
        ) {
            // Sheet content
            Register(
                scope = scope,
                sheetState = bottomSheetState,
                onRegister = vModel::handleRegistration
            )
        }

    }
}