package fi.project.petcare.ui.composables

import android.util.Log
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.ui.ProviderIcon
import io.github.jan.supabase.gotrue.providers.Google

@OptIn(SupabaseExperimental::class)
@Composable
fun GoogleSignInButton(
    modifier: Modifier,
    client: SupabaseClient
) {
    val googleState = client.composeAuth.rememberSignInWithGoogle(
        onResult = { result -> //optional error handling
            when (result) {
                is NativeSignInResult.Success -> { /* Show success message */ }
                is NativeSignInResult.ClosedByUser -> { /* Do nothing */ }
                is NativeSignInResult.Error -> {
                    Log.e(
                        "GoogleSignIn",
                        "Error signing in with Google",
                        result.exception
                    )
                }
                is NativeSignInResult.NetworkError -> {
                    Log.e(
                        "GoogleSignIn",
                        "Network error signing in with Google: ${result.message}"
                    )
                }
            }
        },
    )

    OutlinedButton(
        onClick = { googleState.startFlow() },
        modifier = modifier
    ) {
        Text(text = "Sign in with ")
        ProviderIcon(provider = Google, contentDescription = "Sign in with Google")
    }
}
