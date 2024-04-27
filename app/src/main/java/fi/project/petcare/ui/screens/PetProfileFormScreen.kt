package fi.project.petcare.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Pets
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
import fi.project.petcare.model.data.PetImpl

@Preview
@Composable
fun PetProfileFormScreenPreview() {
//    PetProfileFormScreen(
//        innerPaddingValues = PaddingValues(8.dp),
//
//    )
}

@Composable
fun PetProfileFormScreen(
    innerPaddingValues: PaddingValues,
    newPet: PetImpl,
    onUpdatePet: (PetImpl) -> Unit
) {
    var petNameError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf("") }
    var speciesError by remember { mutableStateOf("") }
    var breedError by remember { mutableStateOf("") }
    var ageMonthsError by remember { mutableStateOf("") }
    var microchipIdError by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .padding(innerPaddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Microchip ID field with validation
            OutlinedTextField(
                value = newPet.microchipId.toString(), // Convert Int to String for TextField
                onValueChange = {
                    onUpdatePet( newPet.copy(microchipId = it.toIntOrNull() ?: 0) )
                    microchipIdError = if (it.isEmpty()) "Microchip ID cannot be empty" else ""
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, // Numeric keyboard type
                    imeAction = ImeAction.Next,
                ),
                shape = MaterialTheme.shapes.large,
                label = {
                    Text(
                        text = "Microchip ID",
                    )
                },
                leadingIcon = { Icon(imageVector = Icons.Default.Numbers, contentDescription = null) },
                isError = microchipIdError.isNotEmpty(),
                singleLine = true,
            )
            Text(
                text = microchipIdError,
                color = Color.Red
            )
        }
        item {
            // Pet name field with validation
            OutlinedTextField(
                value = newPet.name,
                onValueChange = {
                    onUpdatePet(newPet.copy(name = it))
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
                singleLine = true
            )
            Text(
                text = petNameError,
                color = Color.Red,
            )
        }
        item {
            // Gender field with validation
            OutlinedTextField(
                value = newPet.gender,
                onValueChange = {
                    onUpdatePet(newPet.copy(gender = it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                shape = MaterialTheme.shapes.large,
                label = { Text("Gender") },
                leadingIcon = { Icon(imageVector = Icons.Default.Category, contentDescription = null) },
                isError = genderError.isNotEmpty(),
                singleLine = true
            )
            Text(
                text = genderError,
                color = Color.Red,
            )
        }
        item {
            // Weight field with validation
            OutlinedTextField(
                value = newPet.weight.toString(),
                onValueChange = {
                    onUpdatePet(newPet.copy(weight = it.toDoubleOrNull() ?: 0.0))
                    weightError = if (it.isEmpty()) "Weight cannot be empty" else ""
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                shape = MaterialTheme.shapes.large,
                label = { Text("Weight") },
                leadingIcon = { Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = null) },
                isError = weightError.isNotEmpty(),
                singleLine = true
            )
            Text(
                text = weightError,
                color = Color.Red,
            )
        }
        item {
            // Species field with validation
            OutlinedTextField(
                value = newPet.species,
                onValueChange = {
                    onUpdatePet(newPet.copy(species = it))
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
                singleLine = true
            )
            Text(
                text = speciesError,
                color = Color.Red,
            )
        }
        item {
            // Breed field with validation
            OutlinedTextField(
                value = newPet.breed,
                onValueChange = {
                    onUpdatePet(newPet.copy(breed = it))
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
                singleLine = true
            )
            Text(
                text = breedError,
                color = Color.Red,
            )
        }
        item {
            // Birthdate field with validation
            OutlinedTextField(
                value = newPet.ageMonths.toString(), // Convert Double to String for TextField
                onValueChange = {
                    onUpdatePet(newPet.copy(ageMonths = it.toIntOrNull() ?: 0))
                    ageMonthsError = if (it.isEmpty()) "Age cannot be empty" else ""
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                shape = MaterialTheme.shapes.large,
                label = { Text("Age") },
                leadingIcon = { Icon(imageVector = Icons.Default.CalendarViewMonth, contentDescription = null) },
                isError = ageMonthsError.isNotEmpty(),
                singleLine = true
            )

            Text(
                text = ageMonthsError,
                color = Color.Red
            )
        }
    }
}
