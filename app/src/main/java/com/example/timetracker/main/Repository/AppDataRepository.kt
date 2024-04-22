package com.example.timetracker.main.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.timetracker.main.Database.LocalDatabase
import com.example.timetracker.main.Model.AppData

class AppDataRepository(private val context: Context){
    fun insertAppData(appData: ArrayList<AppData>)
    {
        LocalDatabase.getInstance(context)?.appDao()?.insertAll(appData)
    }

    fun getAppData(): LiveData<List<AppData>>? {
        return LocalDatabase.getInstance(context)?.appDao()?.getAll()
    }

    fun deleteALl(){
        LocalDatabase.getInstance(context)?.appDao()?.deleteALl()
    }
}