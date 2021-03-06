package com.codedamon.medshare.donor.ui.addMedicinePage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.codedamon.medshare.model.medicine.Medicine
import com.codedamon.medshare.donor.db.MedShareDatabase
import com.codedamon.medshare.model.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMedicineViewModel(application: Application) : AndroidViewModel(application) {

    val allMedicines: LiveData<List<Medicine>>
    private val repository: MedicineRepository
    init {
        val dao = MedShareDatabase.getDatabase(application).getMedicineDao()
        repository = MedicineRepository(dao)
        allMedicines = repository.allMedicines
    }
    fun addMedicine(medicine: Medicine) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(medicine)
    }

}