package com.example.timetracker.main.ViewModel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.main.Model.AppDataReminder
import com.example.timetracker.main.Repository.AppDataReminderRepository
import com.example.timetracker.main.Utils.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReminderViewModel(private val appDataReminderRepository: AppDataReminderRepository): ViewModel() {

    private val _appList = MutableLiveData<ArrayList<AppDataReminder>>()
    val appLists: LiveData<ArrayList<AppDataReminder>> = _appList


    fun getApps(context: Context)
    {
        CoroutineScope(Dispatchers.IO).launch {
            var apps = ArrayList<AppDataReminder>()
            if (appDataReminderRepository.getAppData()?.size == 0) {
                val packageManager: PackageManager = context.packageManager
                val installedApps: List<ApplicationInfo> =
                    packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                for (appInfo in installedApps) {
                    if (isSocialMediaApp(appInfo.packageName)) {
                        val appIcon = appInfo.loadIcon(packageManager)
                        val bitmap = (appIcon as BitmapDrawable).bitmap
                        val appReminder = AppDataReminder(
                            appInfo.packageName,
                            false,
                            null,
                            null,
                            Utility.encodeImage(bitmap)
                        )
                        apps.add(appReminder)
                    }
                }
            } else {
                apps = ArrayList(appDataReminderRepository.getAppData())
            }
            _appList.postValue(apps)
        }
    }

    fun insertAppData(data: ArrayList<AppDataReminder>)
    {
        CoroutineScope(Dispatchers.IO).launch {
            appDataReminderRepository.insertAppData(data)
        }
    }
    fun updateReminder(appName: String, isChecked: Boolean,currentTime: Long?, timeDifference: Long?)
    {
        CoroutineScope(Dispatchers.IO).launch {
            appDataReminderRepository.updateReminder(appName,isChecked, currentTime,timeDifference)
        }
    }
    fun isSocialMediaApp(packageName: String): Boolean {
        val socialMediaPackageNames = listOf(
            "com.facebook.katana",       // Facebook
            "com.instagram.android",     // Instagram
            "com.twitter.android",       // Twitter
            "com.snapchat.android",      // Snapchat
            "com.pinterest",             // Pinterest
            "com.linkedin.android",      // LinkedIn
            "com.whatsapp",              // WhatsApp
            "com.viber.voip",            // Viber
            "com.skype.raider",          // Skype
            "com.tumblr",                // Tumblr
            "com.reddit.frontpage",      // Reddit
            "com.google.android.youtube" // YouTube
            // Add more social media app package names as needed
        )
        return socialMediaPackageNames.contains(packageName)
    }
}