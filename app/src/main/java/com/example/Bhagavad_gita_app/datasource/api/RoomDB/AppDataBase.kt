package com.example.Bhagavad_gita_app.datasource.api.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [savedChapters :: class,savedVerse::class], version = 2, exportSchema = false)
@TypeConverters(AppTypeConverter::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun savedChaptersDao(): SavedChaptersDao
    abstract fun savedVersesDao():SavedVersesDao

    companion object{
        @Volatile
        var Instance : AppDataBase? = null

        fun getDatabaseInstance(context: Context): AppDataBase?{
            val temInstant : AppDataBase? = Instance
            if (Instance != null) {
                return temInstant
            }
            synchronized(this){
                val roomDb = Room.databaseBuilder(context, AppDataBase::class.java,"AppDatabase")
                    .fallbackToDestructiveMigration().build()
                Instance = roomDb
                return roomDb
            }
        }
    }
}