package fi.project.petcare.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CatchingPokemon
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fi.project.petcare.R
import fi.project.petcare.model.data.PetResponse
import fi.project.petcare.ui.composables.FullScreenModal
import fi.project.petcare.ui.composables.LoadingIndicator
import fi.project.petcare.ui.theme.bg_gr
import fi.project.petcare.viewmodel.PetUiState
import fi.project.petcare.viewmodel.PetViewModel

//@Preview(showBackground = true)
//@Composable
//fun PreviewPetListScreen() {
//    Surface {
//        PetListScreen(
//            petState = PetUiState.Success(
//                listOf(
//                    PetResponse.Pet(
//                        name = "Max",
//                        breed = "Golden Retriever",
//                        weight = 25.0,
//                        species = "Dog",
//                        ageMonths = 12,
//                        gender = "Male",
//                        notes = "Very friendly and playful",
//                        microchipId = 12456,
//                        ownerId = "1234567890"
//                    ),
//                    PetResponse.Pet(
//                        name = "Luna",
//                        breed = "Siamese",
//                        weight = 5.0,
//                        species = "Cat",
//                        ageMonths = 12,
//                        gender = "Female",
//                        notes = "Very friendly and playful",
//                        microchipId = 12456,
//                        ownerId = "1234567890"
//
//                    )
//                )
//            )
//        )
//    }
//}

@Composable
fun PetHeader(
    name: String,
    toggleShowFullDialog: () -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            onClick = { toggleShowFullDialog() }
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
    petViewModel: PetViewModel,
    petState: PetUiState,
    showModal: Boolean,
    toggleShowModal: () -> Unit
) {
    when (petState) {
        is PetUiState.Loading -> {
            LoadingIndicator(
                modifier = Modifier.aspectRatio(1f),
                color = bg_gr
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
                            toggleShowFullDialog = toggleShowModal
                        )
                    }
                    item {
                        PetInfo(
                            pet = pet
                        )
                    }
                }
            }
            val pet = petState.pets.first()
            FullScreenModal(
                showModal = showModal,
                toggleShowFullModal = toggleShowModal,
                onSubmit = petViewModel::upsertPet,
                pet = pet
            )
        }
        is PetUiState.Error -> {
            Text(text = petState.message)
        }
    }
}

@Composable
fun CardsInfoRow(
    pet: PetResponse.Pet
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Card (
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector =
                    if (pet.gender == "Female") Icons.Default.Female
                    else Icons.Default.Male,
                    contentDescription = "sex"
                )
                Text(
                    text = pet.gender,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
        Card (
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.MonitorWeight,
                    contentDescription = "Weight"
                )
                Text(
                    text = pet.weight.toString() + " kg",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
        Card (
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cake,
                    contentDescription = "Age"
                )
                Text(
                    text = pet.ageMonths.toString() + " m",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Composable
fun PetInfo(
    pet: PetResponse.Pet
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Card (
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large.copy(
                bottomStart = MaterialTheme.shapes.extraSmall.bottomStart,
                bottomEnd = MaterialTheme.shapes.extraSmall.bottomStart
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if (pet.imgUrl != null) {
                    Image(
                        painter = painterResource(id = R.drawable.pet_icon_1),
                        contentDescription = "Pet Cover Photo",
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.CatchingPokemon,
                        contentDescription = "Pet Icon",
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = pet.name,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = pet.breed,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
        CardsInfoRow(pet = pet)
        Card (
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = pet.species,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Text(
                    text = pet.notes ?: "No behavioral notes",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large.copy(
                topStart = MaterialTheme.shapes.extraSmall.bottomStart,
                topEnd = MaterialTheme.shapes.extraSmall.bottomStart
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Policy Number",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "456DFDS789",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column (
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Microchip ID",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = pet.microchipId.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
