package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.outlined.Transgender
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.PetResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetProfileForm(
    innerPaddingValues: PaddingValues,
    newPet: PetResponse.Pet,
    onUpdatePet: (PetResponse.Pet) -> Unit,
    onEnableSave: () -> Unit
) {
    var expandedMenu by remember { mutableStateOf(false) }
    val genderOptions = listOf("Female", "Neutered", "Male")
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var microchipId by remember { mutableStateOf("") }
    var petNameError by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf("") }
    var speciesError by remember { mutableStateOf("") }
    var breedError by remember { mutableStateOf("") }
    var ageMonthsError by remember { mutableStateOf("") }
    var microchipIdError by remember { mutableStateOf("") }
    if (
        newPet.name.isNotEmpty() &&
        newPet.species.isNotEmpty() &&
        newPet.breed.isNotEmpty() &&
        age.isNotEmpty()
    ) {
        onEnableSave()
    }

    LazyColumn(
        modifier = Modifier
            .padding(innerPaddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                label = { Text("Name") },
                leadingIcon = { Icon(imageVector = Icons.Default.Pets, contentDescription = null) },
                isError = petNameError.isNotEmpty(),
                singleLine = true,
                supportingText = {
                    Text(
                        text = petNameError
                    )
                }
            )
        }
        item {
            // Microchip ID field with validation
            OutlinedTextField(
                value = microchipId, // Convert Int to String for TextField
                onValueChange = {
                    microchipId = it
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
                supportingText = {
                    Text(
                        text = microchipIdError
                    )
                }
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
                singleLine = true,
                supportingText = {
                    Text(
                        text = speciesError
                    )
                }
            )
        }
        item {
            ExposedDropdownMenuBox(
                expanded = expandedMenu,
                onExpandedChange = { expandedMenu = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = newPet.gender,
                    onValueChange = {
                        onUpdatePet(newPet.copy(gender = it))
                    },
                    shape = MaterialTheme.shapes.large,
                    label = { Text("Sex") },
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Transgender, contentDescription = null) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMenu)
                    },
                    singleLine = true,
                    supportingText = {
                        Text(
                            text = petNameError
                        )
                    }
                )
                ExposedDropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false }
                ) {
                    genderOptions.forEach { gender ->
                        DropdownMenuItem(
                            text = { Text(gender) },
                            onClick = {
                                onUpdatePet(newPet.copy(gender = gender))
                                expandedMenu = false

                            }
                        )
                    }
                }
            }
        }
        item {
            // Weight field with validation
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    onUpdatePet(
                        newPet.copy(
                            weight = it.replace(",", ".").toDoubleOrNull() ?: 0.0
                        )
                    )
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
                singleLine = true,
                supportingText = {
                    Text(
                        text = weightError
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
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
                singleLine = true,
                supportingText = {
                    Text(
                        text = breedError
                    )
                }
            )
        }
        item {
            // Birthdate field with validation
            OutlinedTextField(
                value = age, // Convert Double to String for TextField
                onValueChange = {
                    age = it
                    onUpdatePet(newPet.copy(ageMonths = it.toIntOrNull() ?: 0))
                    ageMonthsError = if (it.isEmpty()) "Age cannot be empty" else ""
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
                shape = MaterialTheme.shapes.large,
                label = { Text("Age months") },
                leadingIcon = { Icon(imageVector = Icons.Default.CalendarViewMonth, contentDescription = null) },
                isError = ageMonthsError.isNotEmpty(),
                singleLine = true,
                supportingText = {
                    Text(
                        text = ageMonthsError
                    )
                }
            )
        }
        item {
            // Birthdate field with validation
            OutlinedTextField(
                value = newPet.notes ?: "",
                onValueChange = {
                    onUpdatePet(newPet.copy(notes = it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                shape = MaterialTheme.shapes.large,
                label = { Text("Behavior") },
                leadingIcon = { Icon(imageVector = Icons.Default.NoteAlt, contentDescription = null) },
                isError = ageMonthsError.isNotEmpty(),
                singleLine = true,
                supportingText = {
                    Text(
                        text = ageMonthsError
                    )
                }
            )
        }
    }
}
