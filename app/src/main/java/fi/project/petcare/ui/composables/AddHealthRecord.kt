package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.HealthRecord
import fi.project.petcare.model.data.HealthRecordType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import fi.project.petcare.model.data.HealthRecordState


@Composable
fun AddHealthRecord() {
    val healthRecordState by remember { mutableStateOf(HealthRecordState()) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Type selection
        DropdownMenu(
            expanded = false, // change to true when clicked
            onDismissRequest = { /* Dismiss the dropdown if needed */ },
            modifier = Modifier.padding(8.dp)
        ) {
            // DropdownMenuItem should be placed here, inside the DropdownMenu's child lambda
            HealthRecordType.entries.forEach { recordType ->
                DropdownMenuItem(
                    text= {Text(recordType.name)},
                    onClick = { healthRecordState.type = recordType }
                )
            }
        }


        // Date selection
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        OutlinedTextField(
            value = dateFormat.format(healthRecordState.date),
            onValueChange = {
                healthRecordState.date = dateFormat.parse(it) ?: Date()
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
            value = healthRecordState.details,
            onValueChange = { healthRecordState.details = it },
            label = { Text("Details") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

            // Additional fields based on record type
        when (healthRecordState.type) {
            HealthRecordType.OPERATION -> {
                // Additional fields for operation record
                OutlinedTextField(
                    value = healthRecordState.operation,
                    onValueChange = { healthRecordState.operation = it },
                    label = { Text("Operation Field") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            HealthRecordType.VETERINARIAN_VISIT -> {
                    // Additional fields for veterinarian visit record
                    OutlinedTextField(
                        value = healthRecordState.veterinarianvisit,
                        onValueChange = { healthRecordState.veterinarianvisit = it },
                        label = { Text("Veterinarian Visit Field") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

            HealthRecordType.MEDICATION -> {
                    // Additional fields for medication record
                    OutlinedTextField(
                        value = healthRecordState.medication,
                        onValueChange = { healthRecordState.medication = it },
                        label = { Text("Medication Field") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

            HealthRecordType.SYMPTOM -> {
                    // Additional fields for symptom record
                    OutlinedTextField(
                        value = healthRecordState.symptom,
                        onValueChange = { healthRecordState.symptom = it },
                        label = { Text("Symptom Field") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

            HealthRecordType.ALLERGY -> {
                    // Additional fields for allergy record
                    OutlinedTextField(
                        value = healthRecordState.allergy,
                        onValueChange = { healthRecordState.allergy = it },
                        label = { Text("Allergy Field") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

            HealthRecordType.EXERCISE -> {
                    // Additional fields for exercise record
                    OutlinedTextField(
                        value = healthRecordState.exercise,
                        onValueChange = { healthRecordState.exercise = it },
                        label = { Text("Exercise Field") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

            HealthRecordType.WEIGHT_MEASUREMENT -> {
                    // Additional fields for weight measurement record
                    OutlinedTextField(
                        value = healthRecordState.weight,
                        onValueChange = { healthRecordState.weight = it },
                        label = { Text("Weight Measurement Field") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }

            // Save button
        Button(
            onClick = {
                val healthRecord = HealthRecord(
                    healthRecordState.type,
                    healthRecordState.date,
                    healthRecordState.details
                )
                // Save it on the database here
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


