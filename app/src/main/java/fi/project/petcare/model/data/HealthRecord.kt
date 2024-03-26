package fi.project.petcare.model.data

import java.util.*

data class HealthRecord(
    val type: HealthRecordType,
    val date: Date,
    val details: String
)

enum class HealthRecordType {
    OPERATION,
    VETERINARIAN_VISIT,
    MEDICATION,
    SYMPTOM,
    ALLERGY,
    EXERCISE,
    WEIGHT_MEASUREMENT
}
