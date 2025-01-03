package com.example.pharmalink

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pharmalink.dao.CartDao
import com.example.pharmalink.dao.MedicineDao
import com.example.pharmalink.dao.UserDao
import com.example.pharmalink.dao.VitaminDao
import com.example.pharmalink.models.CartItem
import com.example.pharmalink.models.Medicine
import com.example.pharmalink.models.User
import com.example.pharmalink.models.Vitamin

@Database(entities = [User::class, Medicine::class, Vitamin::class, CartItem::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun medicineDao(): MedicineDao
    abstract fun vitaminDao(): VitaminDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pharmacy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
