package com.codedamon.medshare.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedicineDao {

    @Insert
    suspend fun insert(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("Select * from medicines order by name ASC")
    suspend fun getAllMedicine():LiveData<List<Medicine>>
}