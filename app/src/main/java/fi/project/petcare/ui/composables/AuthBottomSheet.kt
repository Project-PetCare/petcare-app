package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.ui.FormComponent
import io.github.jan.supabase.compose.auth.ui.LocalAuthState
import io.github.jan.supabase.compose.auth.ui.email.OutlinedEmailField
import io.github.jan.supabase.compose.auth.ui.password.OutlinedPasswordField
import io.github.jan.supabase.compose.auth.ui.password.PasswordRule
import io.github.jan.supabase.compose.auth.ui.password.rememberPasswordRuleList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, SupabaseExperimental::class)
@Composable
fun Register(openDialog: () -> Unit , sheetState: SheetState, onRegister: (email: String, password: String) -> Unit) {
    val scope = rememberCoroutineScope()
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val state = LocalAuthState.current
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
        OutlinedEmailField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-Mail") },
            mandatory = email.isNotBlank(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.imePadding()
        )
        OutlinedPasswordField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            rules = rememberPasswordRuleList(PasswordRule.minLength(8), PasswordRule.containsSpecialCharacter(), PasswordRule.containsDigit(), PasswordRule.containsLowercase(), PasswordRule.containsUppercase()),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.imePadding()
        )
        FormComponent("accept_terms") { valid ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Checkbox(
                    checked = valid.value,
                    onCheckedChange = { valid.value = it },
                )
                TextButton(
                    onClick = { openDialog() },
                ) {
                    Text("Accept Terms of Service", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Button(
            onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) run {
                        onRegister(email, password)
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

@OptIn(ExperimentalMaterial3Api::class, SupabaseExperimental::class)
@Composable
fun Login(sheetState: SheetState, onLogin: (email: String, password: String) -> Unit) {
    val scope = rememberCoroutineScope()
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val state = LocalAuthState.current
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
            mandatory = email.isNotBlank(), //once an email is entered, it is mandatory. (which enable validation)
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
            modifier = Modifier.imePadding()
        )
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Forgot password?", color = MaterialTheme.colorScheme.primary)
        }
        Button(
            onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) run {
                        onLogin(email, password)
                    }
                }
            },
            enabled = state.validForm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp)
                .height(58.dp),
        ) {
            Text("Log in")
        }
    }
}