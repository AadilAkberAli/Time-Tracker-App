package com.example.timetracker.main.Model

import android.graphics.drawable.Drawable
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "AppDataReminder", indices = [Index(value = ["packageName"], unique = true)])
data class AppDataReminder(
    @PrimaryKey(autoGenerate = true) var mid: Long,
    var packageName: String?=null,
    var isReminder : Boolean?=null,
    var currentTime: Long?=null,
    var reminderTime: Long? =null,
    var imageView: String?= null):
    Serializable
{
    constructor(packageName: String? = null,isReminder: Boolean?=null, currentTime: Long?, reminderTime: Long?=null, imageView: String?=null) : this(0,packageName,isReminder,currentTime, reminderTime, imageView)

}
