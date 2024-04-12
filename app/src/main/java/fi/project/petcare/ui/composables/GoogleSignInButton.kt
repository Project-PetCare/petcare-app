package fi.project.petcare.ui.composables

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import fi.project.petcare.BuildConfig
import fi.project.petcare.utils.buildGoogleSignInRequest
import fi.project.petcare.utils.generateHashedNonce
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.ui.ProviderIcon
import io.github.jan.supabase.gotrue.providers.Google
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(SupabaseExperimental::class)
@Composable
fun GoogleSignInButton(coroutineScope: CoroutineScope, modifier: Modifier, onClick: (String, String) -> Unit) {
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val (rawNonce, hashedNonce) = generateHashedNonce()
    val setServerClientId = BuildConfig.SERVER_CLIENT_ID
    val request: GetCredentialRequest = buildGoogleSignInRequest(
        alreadyUser = false,
        autoSelectAccount = true,
        serverClientId = setServerClientId,
        hashedNonce = hashedNonce
    )

    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                onClick(rawNonce, googleIdToken)
            }
        },
        modifier = modifier
    ) {
        Text(text = "Sign in with ")
        ProviderIcon(provider = Google, contentDescription = "Sign in with Google")
    }
}
