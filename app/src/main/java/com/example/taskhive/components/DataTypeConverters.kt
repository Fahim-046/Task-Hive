package com.example.taskhive.components

import androidx.room.TypeConverter
import com.example.taskhive.domain.model.Project
import com.google.gson.Gson
import java.util.Date

class DataTypeConverters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromProject(project: Project): String = Gson().toJson(project)

    @TypeConverter
    fun toProject(json: String): Project = Gson().fromJson(json, Project::class.java)
}