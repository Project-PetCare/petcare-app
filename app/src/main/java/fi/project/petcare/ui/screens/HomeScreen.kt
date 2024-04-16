package fi.project.petcare.ui.screens

import fi.project.petcare.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

@Composable
fun HomeScreen(onNavigateToProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 16.dp)
    )

     {
         Spacer(modifier = Modifier.height(16.dp))
        // Top bar with text "Pets" and setting icon
        Row(

            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pets",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                modifier = Modifier.weight(1f)
            )
            // Setting icon
        }



        // Pet Icons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PetIcon(imageRes = R.drawable.pet_icon_1, text = "Your Text")
            PetIcon(imageRes = R.drawable.pet_icon_1, text = "Another Text")
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
                    .height(320.dp)
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

