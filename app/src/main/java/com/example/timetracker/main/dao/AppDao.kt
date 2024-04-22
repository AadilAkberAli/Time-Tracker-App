package com.example.timetracker.main.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.timetracker.main.Model.AppData

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(appData: List<AppData>)

    @Query("SELECT * FROM AppData")
    fun getAll(): LiveData<List<AppData>>

    @Query("DELETE FROM AppData")
    fun deleteALl()
}