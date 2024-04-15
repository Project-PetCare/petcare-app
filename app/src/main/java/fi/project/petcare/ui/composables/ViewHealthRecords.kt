package fi.project.petcare.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fi.project.petcare.model.data.HealthRecord
import fi.project.petcare.model.data.HealthRecordType
import java.util.*

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
    val plot = letsPlot(data) {
        x = "date"
        y = "type"
        color = "type"
    } + geomPoint()

    Plot(plot)
}


@Preview
@Composable
fun PreviewViewHealthRecords() {
    ViewHealthRecords()
}

