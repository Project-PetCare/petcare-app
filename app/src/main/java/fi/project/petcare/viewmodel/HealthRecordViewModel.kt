package fi.project.petcare.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fi.project.petcare.model.data.HealthRecord

class HealthRecordViewModel : ViewModel() {
    private val _healthRecords = MutableLiveData<List<HealthRecord>>()
    val healthRecords: LiveData<List<HealthRecord>> = _healthRecords

    init {
        // Initialize with an empty list
        _healthRecords.value = listOf()
    }

    fun addHealthRecord(record: HealthRecord) {
        val currentRecords = _healthRecords.value?.toMutableList() ?: mutableListOf()
        currentRecords.add(record)
        _healthRecords.value = currentRecords
    }
}
