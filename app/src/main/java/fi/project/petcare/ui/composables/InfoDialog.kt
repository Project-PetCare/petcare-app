package fi.project.petcare.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun InfoDialog(
    icon: @Composable () -> Unit?,
    title: String,
    content: String,
    toggleDialog: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { toggleDialog() },
        icon = { icon() },
        title = { Text(text = title) },
        text = { Text(text = content) },
        confirmButton = {
            TextButton(
                onClick = {
                    toggleDialog()
                    onConfirm()
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