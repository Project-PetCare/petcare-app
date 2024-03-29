package fi.project.petcare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Card

import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.CardDefaults


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fi.project.petcare.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(petName: String ,navController: NavController) {
    // Content of the profile screen
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Text(text = "Pet Profile", modifier = Modifier.padding(start = 8.dp))
                }
            },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
//        Text(text = "Pet Name: $petName")
        // Add more profile details as needed


        @Composable
        fun ProfileDetailRow(iconId: Int, label: String, value: String) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null, // You can provide a meaningful description if needed
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Label and value
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = value,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun ProfileScreen(petName: String, navController: NavController) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp, vertical = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }

                    Text(
                        text = "Pet Profile - $petName",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                            .height(500.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pet_icon_1),
                            contentDescription = "Pet Cover Photo",
                            modifier = Modifier.fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )

                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = "Camera Icon",
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .alpha(.5f),
                            tint = Color.Black
                        )


                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.BottomStart)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Basic Info",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = Color.Black
                                    ),
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = "Edit",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Blue
                                    ),
                                    modifier = Modifier.clickable { /* Handle edit click */ }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    ProfileDetailRow(
                                        iconId = R.drawable.name_icon,
                                        label = "Name",
                                        value = "Fluffy"
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    ProfileDetailRow(
                                        iconId = R.drawable.species_icon,
                                        label = "Species",
                                        value = "Dog"
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    ProfileDetailRow(
                                        iconId = R.drawable.sex_icon,
                                        label = "Sex",
                                        value = "Male"
                                    )
                                }

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    ProfileDetailRow(
                                        iconId = R.drawable.breed_icon,
                                        label = "Breed",
                                        value = "Retriever"
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    ProfileDetailRow(
                                        iconId = R.drawable.weight_icon,
                                        label = "Weight",
                                        value = "20Kg"
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    ProfileDetailRow(
                                        iconId = R.drawable.birthdate_icon,
                                        label = "BirthDate",
                                        value = "30/08/2012"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}