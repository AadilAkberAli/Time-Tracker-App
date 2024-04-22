package com.example.timetracker.main.Repository

import android.content.Context
import com.example.timetracker.main.Database.LocalDatabase
import com.example.timetracker.main.Model.AppDataReminder

class AppDataReminderRepository(private val context: Context){
    fun insertAppData(appData: ArrayList<AppDataReminder>)
    {
        LocalDatabase.getInstance(context)?.appReminderDao()?.insertAll(appData)
    }

    fun getAppData():  List<AppDataReminder>? {
        return LocalDatabase.getInstance(context)?.appReminderDao()?.getAll()
    }

    fun updateReminder(appName: String, isChecked: Boolean, currentTime: Long?, timeDifference: Long?)
    {
        LocalDatabase.getInstance(context)?.appReminderDao()?.updateReminder(appName,isChecked,currentTime, timeDifference)
    }

    fun deleteALl(){
        LocalDatabase.getInstance(context)?.appReminderDao()?.deleteALl()
    }
}