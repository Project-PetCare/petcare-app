package fi.project.petcare.ui.composables

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingIndicator(modifier: Modifier, color: Color) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color
    )
}