package com.codedamon.medshare.donor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "donor")
class Donor (val name: String,
val email: Double,
val password: Int
) {
    @PrimaryKey(autoGenerate = true)var id:Int=0
}
