package com.codedamon.medshare.model.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
class Medicine(val name: String,
               val price: Double,
               val quantity: Int,
               val expiryDate: String
               ) {
    @PrimaryKey(autoGenerate = true)var id:Int=0
}