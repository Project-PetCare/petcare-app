package fi.project.petcare.ui.composables

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
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
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context,
                    )
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(result.credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    onClick(rawNonce, googleIdToken)
                } catch (e: GetCredentialCancellationException) {
                    // Do nothing. User chose to cancel sign-in with Google
                    Toast.makeText(context, "Open a PetCare account instead", Toast.LENGTH_SHORT).show()
                    return@launch
                } catch (e: NoCredentialException) {
                    Log.e("RestException", e.toString())
                    Toast.makeText(context, "No Google account found", Toast.LENGTH_SHORT).show()
                } catch (e: GetCredentialException) {
                    // Handle GetCredentialException thrown by `credentialManager.getCredential()`
                    Log.e("GetCredentialException", e.toString())
                    Toast.makeText(context, "Credential manager not available", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("Exception", e.toString())
                }

            }
        },
        modifier = modifier
    ) {
        Text(text = "Sign in with ")
        ProviderIcon(provider = Google, contentDescription = "Sign in with Google")
    }
}
