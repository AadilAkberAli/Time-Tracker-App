package com.example.timetracker.main.Utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Utility {

    companion object{
        fun getAppLabel(packageName: String, context: Context): String {
            var applicationInfo: ApplicationInfo? = null
            try {
                applicationInfo = context.packageManager.getApplicationInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                Log.d("TAG", "The package with the given name cannot be found on the system.")
            }
            return (if (applicationInfo != null) context.packageManager.getApplicationLabel(applicationInfo).toString() else "Unknown")
        }

        fun getTimeDifference(hours: Int, minutes: Int, seconds: Int): Calendar {
            // Get the current time
            val calculatedTime = Calendar.getInstance()

            calculatedTime.add(Calendar.HOUR_OF_DAY, hours)
            calculatedTime.add(Calendar.MINUTE, minutes)
            calculatedTime.add(Calendar.SECOND, seconds)

            return calculatedTime
        }

        fun getTimeConversion(currentTime: Calendar, calculatedTime: Calendar): String {
            var reminder = ""
            val currentYear = currentTime.get(Calendar.YEAR)
            val currentMonth = currentTime.get(Calendar.MONTH)
            val currentDay = currentTime.get(Calendar.DAY_OF_MONTH)

            // Get the calculated date and time
            val calculatedYear = calculatedTime.get(Calendar.YEAR)
            val calculatedMonth = calculatedTime.get(Calendar.MONTH)
            val calculatedDay = calculatedTime.get(Calendar.DAY_OF_MONTH)

            // Check if the current date and calculated date are equal
            val datesAreEqual = currentYear == calculatedYear && currentMonth == calculatedMonth && currentDay == calculatedDay
            val outputFormat = SimpleDateFormat("hh:mm:ss a")
            val outputFormat1 = SimpleDateFormat("dd MMM yyyy, hh:mm a")
            reminder = if (datesAreEqual) {
                "Reminder At ${outputFormat.format(calculatedTime.time)}"
            } else {
                "Reminder On ${outputFormat1.format(calculatedTime.time)}"
            }
            return reminder
        }

        fun compareTimes(): Boolean {
            // Get the current time
            val currentTime = Calendar.getInstance()

            // Create a Calendar instance for your own time (replace with your desired time)
            val yourTime = Calendar.getInstance()
            yourTime.set(Calendar.HOUR_OF_DAY, 0) // Example hour
            yourTime.set(Calendar.MINUTE, 5)     // Example minute
            yourTime.set(Calendar.SECOND, 0)      // Example second

            // Compare the times
            return currentTime.equals(yourTime)
        }

        fun convertLongToTime(time: Long, currentTimeLong: Long): String {
            val calendar = Calendar.getInstance()
            val currentTime = Calendar.getInstance()
            currentTime.timeInMillis = currentTimeLong
            calendar.timeInMillis = time
            return getTimeConversion(currentTime,calendar)
        }

        fun decodeImage(image:String): Bitmap? {
            val decodedString: ByteArray = Base64.decode(image, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            return decodedByte
        }

        fun encodeImage(bm: Bitmap): String? {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG,100,baos);
            val b = baos.toByteArray()
            return Base64.encodeToString(b, Base64.DEFAULT)
        }
    }
}