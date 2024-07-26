package com.example.taskhive.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskhive.data.local.AppDatabase
import com.example.taskhive.domain.model.Project
import com.example.taskhive.domain.model.toUiModel
import com.example.taskhive.presentation.uimodel.ProjectUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel : ViewModel() {
    private val _projects = MutableStateFlow<List<ProjectUiModel>>(emptyList())
    val projects: StateFlow<List<ProjectUiModel>> =
        _projects

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun getProjects(context: Context) = viewModelScope.launch {
        val response = AppDatabase(context).projectDao().getAllProjects()
        if (response.isNotEmpty()) {
            _projects.value = response.map {
                it.toUiModel().copy(numberOfTask = getNumberOfTask(context,it))
            }
        }
        else{
            println("Nothing")
        }
    }

    fun getNumberOfProject(context: Context) = viewModelScope.launch {
        val response = AppDatabase(context).projectDao().getProjectCount()
        _count.value = response
    }

    suspend fun getNumberOfTask(context: Context, project: Project):Int  {
        return AppDatabase(context).taskDao().getTaskCountByProject(project)
    }
}