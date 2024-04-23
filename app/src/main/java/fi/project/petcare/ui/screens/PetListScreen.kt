package fi.project.petcare.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.ui.composables.CreateScatterPlot
import fi.project.petcare.ui.composables.getDummyHealthRecords

@Preview(showBackground = true)
@Composable
fun PreviewPetListScreen() {
    Surface {
        PetListScreen(onNavigateToProfile = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetListScreen(
    onNavigateToProfile: () -> Unit
) {
    val headers = listOf(
        "Fluffy",
        "Fido",
        "Rex",
    )

    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        headers.forEach { header ->
            stickyHeader {
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
                        text = header,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = "pet-name",
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = { onNavigateToProfile() },
                        modifier = Modifier.weight(1f).padding(start = 24.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text ="Go to profile",
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
            items(3) { item ->
                Card (
                    modifier = Modifier
                        .size(450.dp, 150.dp)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)

                ) {
                    Text(
                        text = "Some item $item",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )}
                    Card (
                        modifier = Modifier
                            .size(450.dp, 150.dp)
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)

                    ) {
                    // Add a spacer
                    Spacer(modifier = Modifier.height(8.dp))

                    // Add the scatter plot
                    CreateScatterPlot(getDummyHealthRecords())
                }
            }
        }
    }
}