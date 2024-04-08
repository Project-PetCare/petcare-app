package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.HealthRecord
import fi.project.petcare.model.data.HealthRecordType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddHealthRecord() {
    val context = LocalContext.current
    var type by remember { mutableStateOf(HealthRecordType.OPERATION) }
    var date by remember { mutableStateOf(Date()) }
    var details by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Type selection
        HealthRecordType.entries.forEach { recordType ->
            Button(
                onClick = { type = recordType },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = recordType.name)
            }
        }

        // Date selection
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        OutlinedTextField(
            value = dateFormat.format(date),
            onValueChange = {
                date = dateFormat.parse(it) ?: Date()
            },
            label = { Text("Date") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle Done action if needed */ }
            ),
            modifier = Modifier.padding(8.dp)
        )

        // Details input
        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("Details") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        // Additional fields based on record type
        when (type) {
            HealthRecordType.OPERATION -> {
                // Additional fields for operation record
                OutlinedTextField(
                    value = "", // Initialize with empty string
                    onValueChange = { /* Update operation field value */ },
                    label = { Text("Operation Field") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }
            HealthRecordType.VETERINARIAN_VISIT -> {
                // Additional fields for veterinarian visit record
                OutlinedTextField(
                    value = "", // Initialize with empty string
                    onValueChange = { /* Update veterinarian visit field value */ },
                    label = { Text("Veterinarian Visit Field") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }
            // Add cases for other record types similarly
            // Example:
            // HealthRecordType.MEDICATION -> { /* Add medication fields */ }
            HealthRecordType.MEDICATION -> TODO()
            HealthRecordType.SYMPTOM -> TODO()
            HealthRecordType.ALLERGY -> TODO()
            HealthRecordType.EXERCISE -> TODO()
            HealthRecordType.WEIGHT_MEASUREMENT -> TODO()
        }

        // Save button
        Button(
            onClick = {
                // Save the health record
                val healthRecord = HealthRecord(type, date, details)
                // Here you can save the health record to your database or perform any necessary action
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Save")
        }
    }
}

@Preview
@Composable
fun PreviewAddHealthRecord() {
    AddHealthRecord()
}


