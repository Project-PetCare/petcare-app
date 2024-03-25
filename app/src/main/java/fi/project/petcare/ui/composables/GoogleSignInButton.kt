package fi.project.petcare.ui.composables


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import fi.project.petcare.BuildConfig
import fi.project.petcare.viewmodel.AuthViewModel
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.ui.ProviderIcon
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.providers.Google
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@OptIn(SupabaseExperimental::class)
@Composable
fun GoogleSignInButton(vModel: AuthViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        // Generate a nonce and hash it with sha-256
        // Providing a nonce is optional but recommended
        val rawNonce = UUID.randomUUID().toString() // Generate a random String. UUID should be sufficient, but can also be any other random string.
        val bytes = rawNonce.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) } // Hashed nonce to be passed to Google sign-in


        val setServerClientId = BuildConfig.SERVER_CLIENT_ID
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(setServerClientId)
            .setNonce(hashedNonce) // Provide the nonce if you have one
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.i("Google ID Token", googleIdToken)
                Toast.makeText(context, "You have signed in!", Toast.LENGTH_SHORT).show()

//                Connect to Supabase
//                vModel.supabase.auth.signInWith(IDToken) {
//                    idToken = googleIdToken
//                    provider = Google
//                    nonce = rawNonce
//                }

                // Handle successful sign-in
            } catch (e: GetCredentialException) {
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
                Log.e("GetCredentialException", e.toString())
            } catch (e: IllegalArgumentException) {
                // Handle IllegalArgumentException thrown by `GoogleIdTokenCredential.createFrom()`
                Log.e("GoogleIdTokenCredential", e.toString())
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
                Log.e("GoogleIdTokenParsingException", e.toString())
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                Log.e("RestException", e.toString())
            } catch (e: Exception) {
                // Handle unknown exceptions
                Log.e("Exception", e.toString())
            }
        }
    }

    OutlinedButton(
        onClick = onClick, // Continue with Google
        modifier = Modifier.size(width = 160.dp, height = 58.dp)
    ) {
        Text(text = "Sign in with ")
        ProviderIcon(provider = Google, contentDescription = "Google sign in")
    }
}
