package fi.project.petcare.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.HealthRecord
import fi.project.petcare.model.data.HealthRecordType
import java.util.Date

// Dummy function to fetch health records (replace with actual implementation)
fun getDummyHealthRecords(): List<HealthRecord> {
    return listOf(
        HealthRecord(HealthRecordType.OPERATION, Date(), "Details of operation"),
        HealthRecord(HealthRecordType.VETERINARIAN_VISIT, Date(), "Details of vet visit"),
        HealthRecord(HealthRecordType.MEDICATION, Date(), "Details of medication"),
        HealthRecord(HealthRecordType.SYMPTOM, Date(), "Details of symptom"),
        HealthRecord(HealthRecordType.ALLERGY, Date(), "Details of allergy"),
        HealthRecord(HealthRecordType.EXERCISE, Date(), "Details of exercise"),
        HealthRecord(HealthRecordType.WEIGHT_MEASUREMENT, Date(), "Details of weight measurement")
    )
}

@Composable
fun ViewHealthRecords() {
    val healthRecords = getDummyHealthRecords()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "View Health Records")

        Spacer(modifier = Modifier.height(16.dp))

        // Display each health record
        healthRecords.forEach { record ->
            Text("${record.type}: ${record.date} - ${record.details}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Create scatter plot
        CreateScatterPlot(healthRecords)
    }
}

@Composable
fun CreateScatterPlot(data: List<HealthRecord>) {
    val plotWidth = 400f
    val plotHeight = 400f
    val margin = 20f

    Canvas(modifier = Modifier.size(plotWidth.dp)) {
        // Calculate the scaling factors for the x and y axes
        val xValues = data.map { it.date.time.toFloat() }
        val yValues = data.map { it.type.ordinal.toFloat() }

        // Check if xValues and yValues are not null
        if (xValues.isNotEmpty() && yValues.isNotEmpty()) {
            val xRange = xValues.maxOrNull()!! - xValues.minOrNull()!!
            val yRange = yValues.maxOrNull()!! - yValues.minOrNull()!!
            val xScale = (plotWidth - 2 * margin) / xRange
            val yScale = (plotHeight - 2 * margin) / yRange

            // Draw the data points
            data.forEach { record ->
                val x = margin + (record.date.time.toFloat() - xValues.minOrNull()!!) * xScale
                val y = plotHeight - margin - (record.type.ordinal.toFloat() - yValues.minOrNull()!!) * yScale
                drawCircle(color = Color.White, radius = 5f, center = Offset(x, y))
            }
        }
    }
}





@Preview(showSystemUi = true)
@Composable
fun PreviewViewHealthRecords() {
    ViewHealthRecords()
}

