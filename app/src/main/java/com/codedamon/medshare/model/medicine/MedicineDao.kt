package com.codedamon.medshare.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("Select * from medicines order by name ASC")
    fun getAllMedicine():LiveData<List<Medicine>>

}