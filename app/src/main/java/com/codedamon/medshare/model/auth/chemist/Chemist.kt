package com.codedamon.medshare.model.auth.chemist
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "chemist")
class Chemist (val name: String,
             val email: Double,
             val password: Int
) {
    @PrimaryKey(autoGenerate = true)var id:Int=0
}
