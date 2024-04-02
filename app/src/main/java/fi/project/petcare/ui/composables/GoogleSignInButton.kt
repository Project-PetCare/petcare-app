package fi.project.petcare.ui.composables

import android.content.Context
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.ui.ProviderIcon
import io.github.jan.supabase.gotrue.providers.Google

@OptIn(SupabaseExperimental::class)
@Composable
fun GoogleSignInButton(modifier: Modifier, onClick: (context: Context) -> Unit) {
    val context = LocalContext.current

    OutlinedButton(
        onClick = { onClick(context) },
        modifier = modifier
    ) {
        Text(text = "Sign in with ")
        ProviderIcon(provider = Google, contentDescription = "Google sign in")
    }
}
