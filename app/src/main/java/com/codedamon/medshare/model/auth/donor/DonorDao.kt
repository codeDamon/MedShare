package com.codedamon.medshare.model.auth.donor

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.codedamon.medshare.donor.model.Donor


@Dao
interface DonorDao {


//    @OnConflictStrategy(insert())
    @Insert
    suspend fun insert(donor: Donor)

    @Delete
    suspend fun delete(donor: Donor)

    /**
    @Query("Select * from donors order by name ASC")
    fun getAllDonors(): LiveData<List<Donor>>
    */
}