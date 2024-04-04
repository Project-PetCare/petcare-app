package fi.project.petcare.utils

import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import java.security.MessageDigest
import java.util.UUID

// Generate a nonce and hash it with sha-256. Providing a nonce is optional but recommended
fun generateHashedNonce(): Pair<String, String> {
    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
    return Pair(rawNonce, hashedNonce)
}

fun buildGoogleSignInRequest(alreadyUser: Boolean, autoSelectAccount: Boolean, serverClientId: String, hashedNonce: String): GetCredentialRequest {
    val googleIdOptions: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(alreadyUser)
        .setAutoSelectEnabled(autoSelectAccount)
        .setServerClientId(serverClientId)
        .setNonce(hashedNonce)
        .build()

    return GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOptions)
        .build()
}
