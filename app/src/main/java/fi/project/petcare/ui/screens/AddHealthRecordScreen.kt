package fi.project.petcare.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fi.project.petcare.ui.composables.AddHealthRecord

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddHealthRecordScreen() {
    Scaffold(
        // App bar title
        topBar = {
            // You can customize the app bar here
        }
    ) {
        // Content area
        AddHealthRecord()
    }
}

@Preview
@Composable
fun PreviewAddHealthRecordScreen() {
    AddHealthRecordScreen()
}
