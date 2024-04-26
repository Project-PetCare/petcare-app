package fi.project.petcare.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CatchingPokemon
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.R
import fi.project.petcare.model.data.PetResponse
import fi.project.petcare.ui.composables.LoadingIndicator
import fi.project.petcare.viewmodel.PetUiState

@Preview(showBackground = true)
@Composable
fun PreviewPetListScreen() {
    Surface {
        PetListScreen(
            onNavigateToProfile = {},
            petState = PetUiState.Success(
                listOf(
                    PetResponse.Pet(
                        name = "Luna",
                        breed = "Golden Retriever",
                        weight = 25.0,
                        species = "Dog",
                        ageMonths = 12,
                        gender = "Male",
                        notes = "Very friendly and playful",
                        microchipId = 12456,
                        ownerId = "1234567890"
                    ),
                    PetResponse.Pet(
                        name = "Milo",
                        breed = "Siamese",
                        weight = 5.0,
                        species = "Cat",
                        ageMonths = 12,
                        gender = "Male",
                        notes = "Very friendly and playful",
                        microchipId = 12456,
                        ownerId = "1234567890"

                    )
                )
            )
        )
    }
}

@Composable
fun PetInfo(
    pet: PetResponse.Pet
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (pet.imgUrl != null) {
                Icon(
                    imageVector = Icons.Outlined.CatchingPokemon,
                    contentDescription = "Pet Icon",
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.pet_icon_1),
                    contentDescription = "Pet Cover Photo",
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = "Name Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Male,
                        contentDescription = "sex",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = pet.gender,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MonitorWeight,
                        contentDescription = "Weight",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = pet.weight.toString() + " kg",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cake,
                        contentDescription = "Age",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = pet.ageMonths.toString() + " months",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
fun PetHeader(
    name: String,
    onNavigateToProfile: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            onClick = { onNavigateToProfile() }
        ) {
            Text(
                text ="Edit"
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetListScreen(
    onNavigateToProfile: () -> Unit,
    petState: PetUiState
) {

    when (petState) {
        is PetUiState.Loading -> {
            LoadingIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
        }
        is PetUiState.Success -> {
            LazyColumn (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                petState.pets.forEach { pet ->
                    stickyHeader {
                        PetHeader(
                            name = pet.name,
                            onNavigateToProfile = onNavigateToProfile
                        )
                    }
                    item {
                        Text(
                            text = "Basic Information",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        PetInfo(
                            pet = pet
                        )
                    }
                    item {
                        Text(
                            text = "Behavioral Notes",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Column (
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = pet.breed,
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    Text(
                                        text = pet.species,
                                    )
                                }
                                Text(
                                    text = pet.notes ?: "No notes available",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        is PetUiState.Error -> {
            Text(text = petState.message)
        }
    }
}