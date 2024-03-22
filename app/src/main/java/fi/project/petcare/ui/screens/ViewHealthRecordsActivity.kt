package fi.project.petcare.ui.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textview.MaterialTextView
import fi.project.petcare.R
import fi.project.petcare.viewmodel.HealthRecordViewModel

class ViewHealthRecordsActivity : AppCompatActivity() {
    private val healthRecordViewModel: HealthRecordViewModel by viewModels()

    private lateinit var healthRecordsTextView: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_health_records)

        healthRecordsTextView = findViewById(R.id.health_records_text_view)

        healthRecordViewModel.healthRecords.observe(this, Observer { healthRecords ->
            val recordsText = StringBuilder()
            healthRecords.forEachIndexed { index, record ->
                recordsText.append("${index + 1}. ${record.details}\n")
            }
            healthRecordsTextView.text = recordsText.toString()
        })
    }
}
