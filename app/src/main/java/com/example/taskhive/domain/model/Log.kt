package com.example.taskhive.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class Log(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String,
    val duration:Int,
    val taskId:Int,
)
