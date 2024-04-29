package fi.project.petcare.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fi.project.petcare.R
import fi.project.petcare.model.data.PetResponse
import fi.project.petcare.ui.composables.FullScreenModal
import fi.project.petcare.ui.composables.LoadingIndicator
import fi.project.petcare.ui.theme.bg_gr
import fi.project.petcare.viewmodel.PetUiState
import fi.project.petcare.viewmodel.PetViewModel

@Composable
fun PetHeader(
    toggleShowFullDialog: () -> Unit,
    onDelete: () -> Unit = {}
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
        IconButton(
            onClick = { onDelete() }
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete Pet"
            )
        }
        TextButton(
            onClick = { toggleShowFullDialog() }
        ) {
            Text(
                text ="Edit"
            )
        }
    }
}

@Composable
fun PetListScreen(
    petState: PetUiState,
    petViewModel: PetViewModel,
    showModal: Boolean,
    toggleShowModal: () -> Unit,
    userId: String
) {
    AnimatedContent(
        targetState = petState,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(1000)
            ) togetherWith fadeOut(animationSpec = tween(2000))
        },
        label = "Animated Content"
    ) {  targetState ->
        when (targetState) {
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
                    if (targetState.pets.isEmpty()) {
                        item {
                            Text(
                                text = "Start by adding a new pet profile!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(targetState.pets) { pet ->
                            PetHeader(
                                onDelete = { petViewModel.deletePet(pet) },
                                toggleShowFullDialog = toggleShowModal
                            )
                            PetInfo(pet = pet)
                        }
                    }
                }
            }
            is PetUiState.Error -> {
                val pet = PetResponse.Pet(
                    name = "Fluffy",
                    species = "Dog",
                    breed = "Golden Retriever",
                    ageMonths = 10,
                    microchipId = 123456789,
                    ownerId = "123fd-789dsf465-4fd6s",
                    notes = "Fluffy is a very friendly dog. He loves to play fetch and go for walks.",
                    weight = 15.0,
                    gender = "Male",
                )
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PetHeader(
                        onDelete = { /* no-op */ },
                        toggleShowFullDialog = toggleShowModal
                    )
                    PetInfo(pet = pet)
                }
            }
        }
    }

    FullScreenModal(
        showModal = showModal,
        toggleShowFullModal = toggleShowModal,
        onSubmit = petViewModel::upsertPet,
        userId = userId
    )
}

@Composable
fun CardsInfoRow(
    pet: PetResponse.Pet
) {
    val sexIcon =
        if (pet.gender == "Female") Icons.Default.Female
        else if (pet.gender == "Male") Icons.Default.Male
        else Icons.Outlined.Circle

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
                    imageVector = sexIcon,
                    contentDescription = "sex"
                )
                Text(
                    text = pet.gender,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
                if (pet.name == "Fluffy") {
                    Image(
                        painter = painterResource(id = R.drawable.pet_icon_1),
                        contentDescription = "Pet Cover Photo",
                        modifier = Modifier
                            .size(98.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_petcare_default),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                        contentDescription = "Pet Cover Photo",
                        modifier = Modifier
                            .size(98.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background),
                        contentScale = ContentScale.Crop
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
                            style = MaterialTheme.typography.headlineMedium,
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