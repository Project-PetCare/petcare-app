package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.PetResponse
import kotlinx.coroutines.launch

//@Preview
//@Composable
//fun FullScreenModalPreview() {
//    FullScreenModal(
//        showDialog = true,
//        toggleShowFullDialog = {},
//        pet = PetResponse.Pet(
//            id = "1",
//            name = "Fluffy",
//            species = "Cat",
//            breed = "Siamese",
//            ageMonths = 3,
//            microchipId = 1234578,
//            ownerId = "1",
//            gender = "Female",
//            weight = 3.5,
//        )
//    )
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenModal(
    showModal: Boolean,
    toggleShowFullModal: () -> Unit,
    onSubmit: (PetResponse.Pet) -> Unit = {},
    pet: PetResponse.Pet
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (showModal) {
        ModalBottomSheet(
            onDismissRequest = { toggleShowFullModal() },
            sheetState = bottomSheetState,
            content = {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                            if (!bottomSheetState.isVisible) {
                                                toggleShowFullModal()
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = "Close dialog"
                                    )
                                }
                            },
                            title = {
                                Text(
                                    text = "Pet profile",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                            if (!bottomSheetState.isVisible) {
                                                onSubmit(pet)
                                                toggleShowFullModal()
                                            }
                                        }
                                    },
                                    modifier = Modifier.padding(end = 16.dp)
                                ) {
                                    Text(
                                        text = "Save",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    // TODO: Add pet profile form
                    repeat(35) {
                        Text(
                            text = "Pet profile form",
                            modifier = Modifier.padding(innerPadding).padding(16.dp)
                        )
                    }
                }
            }
        )
    }
}