package com.codedamon.medshare.ui.boxDisplayPage

import android.app.Application
import androidx.lifecycle.*
import com.codedamon.medshare.model.Medicine
import com.codedamon.medshare.model.MedicineDatabase
import com.codedamon.medshare.model.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoxDisplayViewModel(application: Application) : AndroidViewModel(application) {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMedicines: LiveData<List<Medicine>>
    private val repository:MedicineRepository
    init {
        val dao = MedicineDatabase.getDatabase(application).getMedicineDao()
        repository = MedicineRepository(dao)
        allMedicines = repository.allMedicines
    }


}