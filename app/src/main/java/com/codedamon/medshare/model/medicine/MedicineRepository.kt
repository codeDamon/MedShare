package com.codedamon.medshare.model

import androidx.lifecycle.LiveData

class MedicineRepository(private val medicineDao: MedicineDao) {

    val allMedicines:LiveData<List<Medicine>> = medicineDao.getAllMedicine()

    suspend fun insert(medicine: Medicine){
        medicineDao.insert(medicine)
    }
    suspend fun delete(medicine: Medicine){
        medicineDao.delete(medicine)
    }
}