package com.codedamon.medshare.model.auth.chemist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ChemistDao {


    //    @OnConflictStrategy(insert())
    @Insert
    suspend fun insert(chemist: Chemist)

    @Delete
    suspend fun delete(chemist: Chemist)

    /**
    @Query("Select * from chemists order by name ASC")
    fun getAllDonors(): LiveData<List<Chemist>>
    */
}