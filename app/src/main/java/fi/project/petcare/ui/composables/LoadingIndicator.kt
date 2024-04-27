package fi.project.petcare.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fi.project.petcare.R
import fi.project.petcare.ui.theme.bg_gr

@Composable
fun LoadingIndicator(modifier: Modifier, color: Color) {
    val brush = Brush.radialGradient(
        listOf(bg_gr, MaterialTheme.colorScheme.background),
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(brush)
            .padding(36.dp)
            .paint(
                painterResource(id = R.drawable.ic_dog_loading)
            )
    ) {
        CircularProgressIndicator(
            modifier = modifier,
            color = color
        )
    }
}