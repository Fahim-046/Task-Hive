package com.example.taskhive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskhive.domain.model.Log
import com.example.taskhive.domain.model.Project
import com.example.taskhive.domain.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLog(log: Log): Long

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE project = :project")
    suspend fun getTaskByProjectId(project: Project): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task

    @Query("SELECT COUNT(*) FROM tasks WHERE project = :project")
    suspend fun getTaskCountByProject(project: Project): Int

    @Query("SELECT * FROM logs WHERE taskId = :taskId")
    suspend fun getLogsByTaskId(taskId: Int): List<Log>
}