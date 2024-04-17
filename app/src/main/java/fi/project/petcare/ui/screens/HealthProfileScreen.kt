package fi.project.petcare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.project.petcare.ui.composables.ViewHealthRecords
import fi.project.petcare.ui.composables.getDummyHealthRecords


@Composable
fun HealthProfileScreen(petName: String, navController: NavController) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Card(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Health Info",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Edit",
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Column for Icon, Date, and Details
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Date",
                                style = MaterialTheme.typography.titleSmall,
                            )
                            Text(
                                text = "Details",
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }

                    HorizontalDivider()
                    // Display each health record
                    val healthRecords = getDummyHealthRecords()
                    healthRecords.forEach { record ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Column for Icon, Date, and Details
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = record.date.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Text(
                                    text = record.details,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHealthProfileScreen() {
    HealthProfileScreen()
}
