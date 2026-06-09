package com.example.rpgym.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PlayerEntity::class], version = 1, exportSchema = false)
@TypeConverters(PlayerConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rpgym.db"
                ).build().also { INSTANCE = it }
            }
    }
}
