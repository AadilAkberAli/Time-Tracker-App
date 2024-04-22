package com.example.timetracker.main.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.main.Model.AppData
import com.example.timetracker.main.Repository.AppDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivityViewModel(private val appDataRepository: AppDataRepository) : ViewModel() {


    fun insertAppData(appData: ArrayList<AppData>)
    {
      CoroutineScope(Dispatchers.IO).launch {
            deleteAll()
            appDataRepository.insertAppData(appData)
        }
    }

    fun getAll(): LiveData<List<AppData>>? {
        return appDataRepository.getAppData()
    }

    fun deleteAll()
    {
           CoroutineScope(Dispatchers.IO).launch {
               appDataRepository.deleteALl()
           }
    }
}