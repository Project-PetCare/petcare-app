package fi.project.petcare.viewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import fi.project.petcare.R
import fi.project.petcare.ui.data.HealthRecord
import fi.project.petcare.ui.data.HealthRecordType
import java.util.*

class AddHealthRecordActivity : AppCompatActivity() {
    private val healthRecordViewModel: HealthRecordViewModel by viewModels()

    private lateinit var inputRecordDetails: TextInputEditText
    private lateinit var addButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_health_record)

        inputRecordDetails = findViewById(R.id.input_record_details)
        addButton = findViewById(R.id.add_button)

        addButton.setOnClickListener {
            val recordDetails = inputRecordDetails.text.toString()
            if (recordDetails.isNotEmpty()) {
                val operationRecord = HealthRecord(
                    HealthRecordType.OPERATION,
                    Date(),
                    recordDetails
                )
                healthRecordViewModel.addHealthRecord(operationRecord)
                val veterinarianRecord = HealthRecord(
                    HealthRecordType.VETERINARIAN_VISIT,
                    Date(),
                    recordDetails
                )
                healthRecordViewModel.addHealthRecord(veterinarianRecord)
                finish()
            }
        }
    }
}
