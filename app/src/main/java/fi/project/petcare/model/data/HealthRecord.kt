package fi.project.petcare.model.data

import java.util.*

data class HealthRecord(
    val type: HealthRecordType,
    val date: Date,
    val details: String
)

data class HealthRecordState(
    var type: HealthRecordType = HealthRecordType.OPERATION,
    var date: Date = Date(),
    var details: String = "",
    var operation: String = "",
    var veterinarianvisit: String = "",
    var medication: String = "",
    var symptom: String = "",
    var allergy: String = "",
    var exercise: String = "",
    var weight: String = ""
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
