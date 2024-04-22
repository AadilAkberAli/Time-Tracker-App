package com.example.timetracker.main.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timetracker.main.Repository.AppDataReminderRepository
import com.example.timetracker.main.Repository.AppDataRepository


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

//DataSearchViewModel

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                appDataRepository = AppDataRepository(context)
            ) as T
        }
        else if(modelClass.isAssignableFrom(UsageViewModel::class.java)) {
            return UsageViewModel(
            ) as T
        }
        else if(modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(
                appDataReminderRepository = AppDataReminderRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}