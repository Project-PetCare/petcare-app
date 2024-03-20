package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
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
import io.github.jan.supabase.compose.auth.ui.FormComponent
import io.github.jan.supabase.compose.auth.ui.LocalAuthState
import io.github.jan.supabase.compose.auth.ui.email.OutlinedEmailField
import io.github.jan.supabase.compose.auth.ui.password.OutlinedPasswordField
import io.github.jan.supabase.compose.auth.ui.password.PasswordRule
import io.github.jan.supabase.compose.auth.ui.password.rememberPasswordRuleList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(scope: CoroutineScope, sheetState: SheetState, onRegister: () -> Unit = {}) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val state = LocalAuthState.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, bottom = 72.dp)
    ) {
        Text(
            text = "Sign up",
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
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large
        )
        OutlinedPasswordField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            rules = rememberPasswordRuleList(PasswordRule.minLength(8), PasswordRule.containsSpecialCharacter(), PasswordRule.containsDigit(), PasswordRule.containsLowercase(), PasswordRule.containsUppercase()),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            shape = MaterialTheme.shapes.large
        )
        FormComponent("accept_terms") { valid ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Checkbox(
                    checked = valid.value,
                    onCheckedChange = { valid.value = it },
                )
                Text(
                    text = "Accept Terms and Conditions",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Button(
            onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onRegister()
                    }
                }
            },
            enabled = state.validForm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp)
        ) {
            Text("Sign up")
        }
    }
}

//@Composable
//fun Login() {
//    { /*TODO*/ }
//}