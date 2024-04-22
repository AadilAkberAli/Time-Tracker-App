package com.example.timetracker.main.Model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "AppData", indices = [Index(value = ["packageName"], unique = true)])
data class AppData(
    @PrimaryKey(autoGenerate = true) var mid: Long,
    var packageName: String?=null,
    var time: Long?=null,
    var lastUsed: Long?= null,
    var imageView: String?= null):
    Serializable
{
    constructor(packageName: String? = null, time: Long?= null, lastUsed: Long?=null, imageView: String?=null) : this(0,packageName,time,lastUsed, imageView)


}
