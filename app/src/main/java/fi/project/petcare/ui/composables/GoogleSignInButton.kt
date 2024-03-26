package fi.project.petcare.ui.composables

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.compose.auth.ui.ProviderIcon
import io.github.jan.supabase.gotrue.providers.Google

@OptIn(SupabaseExperimental::class)
@Composable
fun GoogleSignInButton(onClick: (context: Context) -> Unit) {
    val context = LocalContext.current

    OutlinedButton(
        onClick = { onClick(context) },
        modifier = Modifier.size(width = 160.dp, height = 58.dp)
    ) {
        Text(text = "Sign in with ")
        ProviderIcon(provider = Google, contentDescription = "Google sign in")
    }
}
