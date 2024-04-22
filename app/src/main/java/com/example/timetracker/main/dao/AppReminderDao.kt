package com.example.timetracker.main.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.timetracker.main.Model.AppDataReminder

@Dao
interface AppReminderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(appData: List<AppDataReminder>)

    @Query("SELECT * FROM AppDataReminder")
    fun getAll(): List<AppDataReminder>

    @Query("DELETE FROM AppDataReminder")
    fun deleteALl()

    @Query("UPDATE AppDataReminder SET isReminder = :isChecked, reminderTime = :timeDifference, currentTime = :currentTime  WHERE packageName =:appName")
    fun updateReminder(appName: String, isChecked: Boolean, currentTime: Long?, timeDifference: Long?)
}