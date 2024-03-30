package fi.project.petcare.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun InfoDialog(toggleDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = { /* do nothing */ },
        icon = { Icon(Icons.Filled.Handshake, contentDescription = null) },
        title = {
            Text(text = "User Agreement")
        },
        text = {
            Text(
                text = "1. PetCare is for personal use only.\n" +
                        "\n 2. We respect your privacy and won't share your data.\n" +
                        "\n 3. You're responsible for your pet's info accuracy.\n" +
                        "\n 4. Consult a vet for medical advice.\n" +
                        "\n 5. Expect updates to improve PetCare.\n" +
                        "\n Questions? Contact us at [contact@email.com].\n",
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    toggleDialog()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    toggleDialog()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}