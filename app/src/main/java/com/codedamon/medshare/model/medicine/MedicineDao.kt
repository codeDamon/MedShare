package com.codedamon.medshare.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codedamon.medshare.model.medicine.Medicine

@Dao
interface MedicineDao {

  //handle conflicts like if medicine already exists, if doesn't exist, if exist but at different price??? what to do?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("Select * from medicines order by name ASC")
    fun getAllMedicine():LiveData<List<Medicine>>

}