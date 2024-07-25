package com.example.taskhive.presentation.uimodel

import com.example.taskhive.domain.model.Project
import com.example.taskhive.domain.model.Task
import com.example.taskhive.domain.model.TaskStatus
import java.util.Date

data class TaskUiModel(
    val id: Int = 0,
    var title: String,
    var description: String,
    var plannedStartTime: Date? = null,
    var plannedEndTime: Date? = null,
    val actualStartTime: Date? = null,
    val actualEndTime: Date? = null,
    val project: Project,
    var taskStatus: TaskStatus = TaskStatus.TODO
)

fun Task.toUiModel() = TaskUiModel(
    id = id,
    title = title,
    description = description,
    plannedStartTime = plannedStartTime ?: Date(),
    plannedEndTime = plannedEndTime ?: Date(),
    actualStartTime = actualStartTime ?: Date(),
    actualEndTime = actualEndTime ?: Date(),
    project = project,
    taskStatus = taskStatus
)
