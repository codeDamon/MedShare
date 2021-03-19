package com.codedamon.medshare.donor.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codedamon.medshare.model.medicine.Medicine
import com.codedamon.medshare.model.MedicineDao

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Medicine::class], version = 1, exportSchema = false)
abstract class MedShareDatabase : RoomDatabase() {

    abstract fun getMedicineDao(): MedicineDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MedShareDatabase? = null

        fun getDatabase(context: Context): MedShareDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedShareDatabase::class.java,
                    "medicine_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}