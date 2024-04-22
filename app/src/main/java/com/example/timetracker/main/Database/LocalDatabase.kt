package com.example.timetracker.main.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timetracker.main.Model.AppData
import com.example.timetracker.main.Model.AppDataReminder
import com.example.timetracker.main.dao.AppDao
import com.example.timetracker.main.dao.AppReminderDao

@Database(
    entities = [AppData::class, AppDataReminder::class],
    version = 1
)

abstract class LocalDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun appReminderDao(): AppReminderDao
    companion object {
        private var sInstance: LocalDatabase? = null
        const val DATABASE_NAME = "AppData.db"

        fun getInstance(context: Context): LocalDatabase? {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
            }
            return sInstance
        }
    }
}
