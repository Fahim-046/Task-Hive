package com.example.taskhive.presentation.task.log.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskhive.data.local.AppDatabase
import com.example.taskhive.domain.model.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogListViewModel : ViewModel() {
    private val _logs = MutableStateFlow<List<Log>>(emptyList())
    val logs = _logs.asStateFlow()

    fun getLogs(context: Context, taskId: Int) = viewModelScope.launch {
        val logs = AppDatabase(context).taskDao().getLogsByTaskId(taskId)
        if (logs.isNotEmpty()) {
            _logs.value = logs
        }
    }
}