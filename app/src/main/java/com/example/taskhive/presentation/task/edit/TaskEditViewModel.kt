package com.example.taskhive.presentation.task.edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskhive.data.local.AppDatabase
import com.example.taskhive.domain.model.Task
import com.example.taskhive.domain.model.TaskStatus
import com.example.taskhive.presentation.uimodel.TaskUiModel
import com.example.taskhive.presentation.uimodel.toUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskEditViewModel : ViewModel() {
    private val _task = MutableStateFlow<TaskUiModel?>(null)
    val task = _task.asStateFlow()

    fun getTaskById(context: Context, taskId: Int) = viewModelScope.launch {
        val task = AppDatabase(context).taskDao().getTaskById(taskId)
        _task.value = task.toUiModel()
    }

    fun editTask(context: Context, task: TaskUiModel) = viewModelScope.launch {
        AppDatabase(context).taskDao().saveTask(
            Task(
                id = task.id,
                title = task.title,
                description = task.description,
                plannedStartTime = task.plannedStartTime,
                plannedEndTime = task.plannedEndTime,
                project = task.project,
                taskStatus = task.taskStatus
            )
        )
    }

    fun onTitleChange(newTitle:String){
        _task.value = task.value?.copy(title = newTitle)
    }

    fun onDescriptionChange(newDescription:String){
        _task.value = task.value?.copy(description = newDescription)
    }

    fun onTaskStatusChange(newStatus:TaskStatus){
        _task.value = task.value?.copy(taskStatus = newStatus)
    }
}