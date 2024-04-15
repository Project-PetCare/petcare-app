package fi.project.petcare.ui.screens

import fi.project.petcare.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        user = User(name = "John Doe", email = "johndoe@email.com")
    )
}

class User(
    val name: String,
    val email: String
)

class Veterinarian(
    val name: String,
    val location: String,
    val rating: Float,
    val imageUrl: String,
)

val veterinarianList = listOf(
    Veterinarian(
        name = "Dr. John Doe",
        location = "Helsinki",
        rating = 4.5f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. Jane Doe",
        location = "Espoo",
        rating = 4.0f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. John Smith",
        location = "Vantaa",
        rating = 4.2f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. Jane Smith",
        location = "Helsinki",
        rating = 4.8f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. John Johnson",
        location = "Espoo",
        rating = 4.3f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. Jane Johnson",
        location = "Vantaa",
        rating = 4.6f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. John Brown",
        location = "Helsinki",
        rating = 4.7f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. Jane Brown",
        location = "Espoo",
        rating = 4.1f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. John White",
        location = "Vantaa",
        rating = 4.4f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
    Veterinarian(
        name = "Dr. Jane White",
        location = "Helsinki",
        rating = 4.9f,
        imageUrl = "https://www.example.com/image.jpg"
    ),
)

@Composable
fun HomeScreen(
    user: User
) {
    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)

                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                    Column {
                        Text(
                            text = "Hi, ${user.name}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            Text(
                text = "Nearby Veterinarians",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
        items(veterinarianList.size) { index ->
            VeterinarianCard(
                veterinarian = veterinarianList[index],
                onClick = { /* Navigate to veterinarian profile */ }
            )
        }
    }
}

@Composable
fun VeterinarianCard(
    veterinarian: Veterinarian,
    onClick: () -> Unit
) {
    Card (
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Store,
                contentDescription = "Store Icon",
                modifier = Modifier.size(65.dp)
            )
            Column (
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = veterinarian.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = veterinarian.location,
                    )
                    Text(
                        text = "Rating: ${veterinarian.rating}",
                    )
                }
            }
        }

    }
}


@Composable
fun OldHomeScreen(onNavigateToProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 16.dp)
    )
    {
        // Pet Icons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PetIcon(imageRes = R.drawable.pet_icon_1, text = "Your Text")
            PetIcon(imageRes = R.drawable.pet_icon_2, text = "Another Text")
            PetIcon(imageRes = R.drawable.pet_icon_5, text = "More Text")
        }
        // Pet Profile with cover photo
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Cover photo
                Image(
                    painter = painterResource(id = R.drawable.pet_icon_1),
                    contentDescription = "Pet Cover Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 20.dp),
                    contentScale = ContentScale.Crop
                )
                // Pet profile details
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart)
                ) {

                    ClickableRow( { onNavigateToProfile() } ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Fluffy",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowRight, contentDescription = null)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text(
                                text = "Type: Dog",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Gender: Male",
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Age: 3 years",
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Weight: 25kg",
                            )
                        }
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.medical_record_icon1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp) // Adjust the size of the image
                )
                Spacer(modifier = Modifier.width(12.dp))
                // We may use TextButton instead of ClickableText here. You can try it.
                ClickableText(
                    text = AnnotatedString("Medical Records"),
                    onClick = {
                        /* Navigate to new page */
                    },
                    modifier = Modifier.weight(1f),
                )
                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowRight, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Prescription
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.medical_record_icon2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp) // Adjust the size of the image
                )
                Spacer(modifier = Modifier.width(12.dp))
//                ClickableText(
//                    text = AnnotatedString("Prescription"),
//                    onClick = { /* Navigate to new page */ },
//                    modifier = Modifier.weight(1f),
//                    style = TextStyle(
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 20.sp // Increase the font size
//                    )
//                )
                TextButton(
                    onClick = {
                        /* Navigate to new page */
                    },
//                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = "Prescription")
                }
                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowRight, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lab Results
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.labtest),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp) // Adjust the size of the image
                )
                Spacer(modifier = Modifier.width(12.dp))
                ClickableText(
                    text = AnnotatedString("Lab Results"),
                    onClick = {
                        /* Navigate to new page */
                    },
                    modifier = Modifier.weight(1f),
                )
                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowRight, contentDescription = null)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        )
        {
            Spacer(modifier = Modifier.weight(1f))
            FloatingActionButton(
                onClick = { /* Navigate to pet profile form */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Pet Profile")
            }
        }
    }
}



@Composable
fun PetIcon(imageRes: Int, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = CircleShape)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Blue, CircleShape)

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
        )
    }
}

@Composable
fun PetProfile(
    petName: String,
    petAge: Int,
    petType: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pet Profile",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text("Name: $petName")
        Text("Age: $petAge")
        Text("Type: $petType")
    }
}
@Composable
fun ClickableRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}
