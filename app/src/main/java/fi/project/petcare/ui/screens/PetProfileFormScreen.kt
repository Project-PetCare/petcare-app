package fi.project.petcare.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PetProfileFormScreen() {
    // State variables to hold form values and validation errors
    var name by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age_months by remember { mutableStateOf("") }
    val genderOptions = listOf("Male", "Female", "Other")
    var petNameError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf("") }
    var speciesError by remember { mutableStateOf("") }
    var breedError by remember { mutableStateOf("") }
    var age_monthsError by remember { mutableStateOf("") }
    var microchipId by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var microchipIdError by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create a account for you pet",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)


        )

        // Microchip ID field with validation
        OutlinedTextField(
            value = microchipId,
            onValueChange = {
                microchipId = it
                microchipIdError = if (it.isEmpty()) "Microchip ID cannot be empty" else ""
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            label = { Text("Microchip ID") },
            leadingIcon = { Icon(imageVector = Icons.Default.Numbers, contentDescription = null) },
            isError = microchipIdError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = microchipIdError,
            color = Color.Red,


            )

        // Pet name field with validation
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                petNameError = if (it.isEmpty()) "Pet name cannot be empty" else ""
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            label = { Text("Pet Name") },
            leadingIcon = { Icon(imageVector = Icons.Default.Pets, contentDescription = null) },
            isError = petNameError.isNotEmpty(),
            singleLine = true,

            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = petNameError,
            color = Color.Red,


            )

        // Gender field with validation

        OutlinedTextField(
            value = selectedGender,
            onValueChange = { selectedGender = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            label = { Text("Gender") },
            leadingIcon = { Icon(imageVector = Icons.Default.Category, contentDescription = null) },
            isError = genderError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = genderError,
            color = Color.Red,

            )
        // Weight field with validation
        OutlinedTextField(
            value = weight,
            onValueChange = {
                weight = it
                weightError = if (it.isEmpty()) "Weight cannot be empty" else ""
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            label = { Text("Weight") },
            leadingIcon = { Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = null) },
            isError = weightError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = weightError,
            color = Color.Red,

            )

        // Species field with validation
        OutlinedTextField(
            value = species,
            onValueChange = {
                species = it
                speciesError = if (it.isEmpty()) "Species cannot be empty" else ""
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            label = { Text("Species") },
            leadingIcon = { Icon(imageVector = Icons.Default.Animation, contentDescription = null) },
            isError = speciesError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = speciesError,
            color = Color.Red,

            )

        // Breed field with validation
        OutlinedTextField(
            value = breed,
            onValueChange = {
                breed = it
                breedError = if (it.isEmpty()) "Breed cannot be empty" else ""
            },
            label = { Text("Breed") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            leadingIcon = { Icon(imageVector = Icons.Default.Pets, contentDescription = null) },
            isError = breedError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = breedError,
            color = Color.Red,

            )

        // Birthdate field with validation
        OutlinedTextField(
            value = age_months,
            onValueChange = {
                age_months = it
                age_monthsError = if (it.isEmpty()) "Age cannot be empty" else ""
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            shape = MaterialTheme.shapes.large,
            label = { Text("Age") },
            leadingIcon = { Icon(imageVector = Icons.Default.CalendarViewMonth, contentDescription = null) },
            isError = age_monthsError.isNotEmpty(),
            singleLine = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = age_monthsError,
            color = Color.Red,

            )


        // Save button
        Button(
            onClick = {

            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp)
                .height(58.dp)
        ) {
            Text("Submit")
        }
    }}

@Preview
@Composable
fun PetProfileFormScreenPreview() {
    PetProfileFormScreen()
}
