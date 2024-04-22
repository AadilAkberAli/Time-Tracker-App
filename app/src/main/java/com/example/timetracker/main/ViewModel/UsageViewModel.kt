package com.example.timetracker.main.ViewModel

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetracker.R
import com.example.timetracker.main.Model.AppData
import com.example.timetracker.main.Utils.Constants
import com.example.timetracker.main.Utils.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UsageViewModel: ViewModel() {

    private val _appList = MutableLiveData<ArrayList<AppData>>()
    val appLists: LiveData<ArrayList<AppData>> = _appList
    fun getForegroundActivity(usage: String, context: Context)
    {
        CoroutineScope(Dispatchers.IO).launch {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val cal = Calendar.getInstance()
        val currentTime = System.currentTimeMillis()
        var usageEvents : List<UsageStats> = when (usage) {
            Constants.dailyUsage -> {
                cal.add(Calendar.WEEK_OF_YEAR, -1)
                usageStatsManager.queryUsageStats( UsageStatsManager.INTERVAL_DAILY,
                    cal.timeInMillis, currentTime)
            }
            Constants.weekyUsage -> {
                cal.add(Calendar.WEEK_OF_YEAR, -1)
                usageStatsManager.queryUsageStats( UsageStatsManager.INTERVAL_WEEKLY,
                    cal.timeInMillis, currentTime)
            }
            else -> {
                cal.add(Calendar.MONTH, -1)
                usageStatsManager.queryUsageStats( UsageStatsManager.INTERVAL_MONTHLY,
                    cal.timeInMillis, currentTime)
            }
        }
        val listAppData = ArrayList<AppData>()
        usageEvents.forEach {
            if(it.totalTimeInForeground > 0)
            {
                Log.e("Check", it.packageName + DateUtils.formatElapsedTime(it.totalTimeInForeground / 1000))
                val appName = Utility.getAppLabel(it.packageName, context)
                if(appName != context.getString(R.string.app_name))
                {
                    try {
                        val icon = context.packageManager
                            .getApplicationIcon(it.packageName)
                        val appData = AppData(appName, it.totalTimeInForeground, it.lastTimeUsed,Utility.encodeImage(icon.toBitmap()))
                        listAppData.add(appData)
                    } catch (e: PackageManager.NameNotFoundException) {
                        e.printStackTrace()
                        val icon = ContextCompat.getDrawable(context, android.R.drawable.sym_def_app_icon)
                        val appData = AppData(appName, it.totalTimeInForeground, it.lastTimeUsed,
                            icon?.let { it1 -> Utility.encodeImage(it1.toBitmap()) })
                        listAppData.add(appData)
                    }
                }

            }
        }
            _appList.postValue(listAppData)
        }
    }







}