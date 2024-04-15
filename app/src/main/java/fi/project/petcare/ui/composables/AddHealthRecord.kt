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
    val type by remember { mutableStateOf(HealthRecordType.OPERATION) }
    var date by remember { mutableStateOf(Date()) }
    var details by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    var veterinarianvisit by remember { mutableStateOf("") }
    var medication by remember { mutableStateOf("") }
    var symptom by remember { mutableStateOf("") }
    var allergy by remember { mutableStateOf("") }
    var exercise by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Type selection
        repeat(HealthRecordType.entries.size) {
            Text(text = "Text")
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Additional fields based on record type
        //when (type) {
        // HealthRecordType.OPERATION -> {
        // Additional fields for operation record
        OutlinedTextField(
            value = operation,
            onValueChange = { operation = it },
            label = { Text("Operation Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // HealthRecordType.VETERINARIAN_VISIT -> {
        // Additional fields for veterinarian visit record
        OutlinedTextField(
            value = veterinarianvisit,
            onValueChange = { veterinarianvisit = it },
            label = { Text("Veterinarian Visit Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        //  HealthRecordType.MEDICATION -> {
        // Additional fields for medication record
        OutlinedTextField(
            value = medication,
            onValueChange = { medication = it },
            label = { Text("Medication Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // HealthRecordType.SYMPTOM -> {
        // Additional fields for symptom record
        OutlinedTextField(
            value = symptom,
            onValueChange = { symptom = it },
            label = { Text("Symptom Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // HealthRecordType.ALLERGY -> {
        // Additional fields for allergy record
        OutlinedTextField(
            value = allergy,
            onValueChange = { allergy = it },
            label = { Text("Allergy Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // HealthRecordType.EXERCISE -> {
        // Additional fields for exercise record
        OutlinedTextField(
            value = exercise,
            onValueChange = { exercise = it },
            label = { Text("Exercise Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // HealthRecordType.WEIGHT_MEASUREMENT -> {
        // Additional fields for weight measurement record
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight Measurement Field") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )


    // Save button
        Button(
            onClick = {
                // Save the health record
                val healthRecord = HealthRecord(type, date, details, operation, veterinarianvisit, medication, symptom, allergy, exercise, weight)
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
