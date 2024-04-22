package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.compose.auth.ui.FormComponent
import io.github.jan.supabase.compose.auth.ui.LocalAuthState
import io.github.jan.supabase.compose.auth.ui.email.OutlinedEmailField
import io.github.jan.supabase.compose.auth.ui.password.OutlinedPasswordField
import io.github.jan.supabase.compose.auth.ui.password.PasswordRule
import io.github.jan.supabase.compose.auth.ui.password.rememberPasswordRuleList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, SupabaseExperimental::class)
@Composable
fun Register(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    toggleShowSheet: () -> Unit,
    onRegister: (username: String?, email: String, password: String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val state = LocalAuthState.current
    var agreementDialog by remember { mutableStateOf(false) }
    fun toggleDialog() { agreementDialog = !agreementDialog }

    if (agreementDialog) {
        InfoDialog(
            icon = { Icon(Icons.Filled.Handshake, contentDescription = "Agreement") },
            title = "User Agreement",
            content = "1. PetCare is for personal use only.\n" +
                    "\n 2. We respect your privacy and won't share your data.\n" +
                    "\n 3. You're responsible for your pet's info accuracy.\n" +
                    "\n 4. Consult a vet for medical advice.\n" +
                    "\n 5. Expect updates to improve PetCare.\n" +
                    "\n Questions? Contact us at petcare@gmail.com.\n",
            toggleDialog = ::toggleDialog,
            onConfirm = { /* do nothing */ }
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 36.dp)
    ) {
        Text(
            text = "Create account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 72.dp, vertical = 16.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username (optional)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            leadingIcon = {
                Icon(Icons.Outlined.AccountBox, contentDescription = "Account user name")
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedEmailField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-Mail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
        )
        OutlinedPasswordField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            rules = rememberPasswordRuleList(
                PasswordRule.minLength(8),
                PasswordRule.containsSpecialCharacter(),
                PasswordRule.containsDigit(),
                PasswordRule.containsLowercase(),
                PasswordRule.containsUppercase()
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            shape = MaterialTheme.shapes.large,
        )
        FormComponent(key = "accept_terms") { valid ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Checkbox(
                    checked = valid.value,
                    onCheckedChange = { valid.value = it },
                )
                TextButton(
                    onClick = { toggleDialog() },
                ) {
                    Text(
                        text = "Accept Terms of Service",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        Button(
            onClick = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        toggleShowSheet()
                        onRegister(username, email, password)
                    }
                }
            },
            enabled = state.validForm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp)
                .height(58.dp)
        ) {
            Text("Sign up")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, SupabaseExperimental::class, SupabaseInternal::class)
@Composable
fun Login(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    toggleShowSheet: () -> Unit,
    onLogin: (email: String, password: String) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val formState = LocalAuthState.current
    formState.remove(key = "accept_terms")
    formState.remove(key = "email")
    formState.remove(key = "password")

    var forgotPasswordDialog by remember { mutableStateOf(false) }
    fun toggleForgotPasswordDialog() { forgotPasswordDialog = !forgotPasswordDialog }

    if (forgotPasswordDialog) {
        InfoDialog(
            icon = { Icon(Icons.Filled.Password, contentDescription = "Reset password") },
            title = "Reset password",
            content = "1. Make sure your email in the previous step is correct.\n \n 2. Click again in Forgot password?\n\n 3. Click on confirm button.\n\n We will send you a password reset link to your email.",
            toggleDialog = ::toggleForgotPasswordDialog,
            onConfirm = { /* Call updateUser from vModel */ }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 36.dp)
    ) {
        Text(
            text = "Sign in",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 72.dp, vertical = 16.dp)
        )
        OutlinedEmailField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-Mail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large
        )
        OutlinedPasswordField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            rules = rememberPasswordRuleList(PasswordRule.minLength(6)),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            shape = MaterialTheme.shapes.large,
        )
        TextButton(
            onClick = { toggleForgotPasswordDialog() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Forgot password?", color = MaterialTheme.colorScheme.primary)
        }
        Button(
            onClick = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        toggleShowSheet()
                        onLogin(email, password)
                    }
                }
            },
            enabled = formState.validForm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp)
                .height(58.dp),
        ) {
            Text("Log in")
        }
    }
}