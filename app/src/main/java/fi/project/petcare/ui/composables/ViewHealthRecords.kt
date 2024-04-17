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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.HealthRecord
import fi.project.petcare.model.data.HealthRecordType
import java.util.Date

// Dummy function to fetch health records (replace with actual implementation)
fun getDummyHealthRecords(): List<HealthRecord> {
    return listOf(
        HealthRecord(HealthRecordType.OPERATION, Date(2023-5-5), "Details of operation"),
        HealthRecord(HealthRecordType.VETERINARIAN_VISIT, Date(2021-9-7), "Details of vet visit"),
        HealthRecord(HealthRecordType.MEDICATION, Date(2020-3-3), "Details of medication"),
        HealthRecord(HealthRecordType.SYMPTOM, Date(1992-4-6), "Details of symptom"),
        HealthRecord(HealthRecordType.ALLERGY, Date(1999-9-9), "Details of allergy"),
        HealthRecord(HealthRecordType.EXERCISE, Date(2008-3-5), "Details of exercise"),
        HealthRecord(HealthRecordType.WEIGHT_MEASUREMENT, Date(2005-2-5), "Details of weight measurement")
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
    val plotWidth = 900f
    val plotHeight = 900f
    val margin = 20f

    val backgroundColor = Color.White // Choose your desired background color

    Canvas(modifier = Modifier.size(plotWidth.dp)) {
        // Draw background
        drawRect(color = backgroundColor, size = Size(plotWidth, plotHeight))

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
                drawCircle(color = Color.Red, radius = 20f, center = Offset(x, y))
            }
        }
    }
}





@Preview(showSystemUi = true)
@Composable
fun PreviewViewHealthRecords() {
    ViewHealthRecords()
}

