package com.example.taskhive.presentation.task.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskhive.components.CalendarCard
import com.example.taskhive.components.DataInputDialog
import com.example.taskhive.components.ProgressType
import com.example.taskhive.components.TaskCard
import com.example.taskhive.components.TimerButton
import com.example.taskhive.components.TopBar
import com.example.taskhive.domain.model.Log
import com.example.taskhive.domain.model.Project
import com.example.taskhive.domain.model.Task
import com.example.taskhive.domain.model.TaskStatus
import com.example.taskhive.presentation.uimodel.TaskUiModel
import com.example.taskhive.ui.theme.TaskHiveTheme
import com.example.taskhive.ui.theme.appColor
import com.example.taskhive.utils.getReadableTime

@Composable
fun TaskListScreen(
    goBack: () -> Unit,
    goToAddTask: (Int) -> Unit = {},
    goToEditTask: (Int) -> Unit = {},
    projectId: Int? = null,
) {
    val context = LocalContext.current
    val viewModel: TaskListViewModel = viewModel()
    LaunchedEffect(Unit) {
        if (projectId != null) {
            viewModel.getTasksById(projectId, context)
        } else {
            viewModel.getAllTasks(context)
        }
    }
    LaunchedEffect(projectId) {
        if (projectId != null) {
            viewModel.getProjectById(projectId, context)
        }
    }

    val tasks by viewModel.tasks.collectAsState()
    val project by viewModel.project.collectAsState()
    TaskListScreenSkeleton(
        goBack = goBack,
        goToAddTask = goToAddTask,
        goToEditTask = goToEditTask,
        projectId = projectId,
        tasks = tasks,
        project = project,
        saveLog = { log ->
            viewModel.saveLog(context, log)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskListScreenSkeletonPreview() {
    TaskHiveTheme {
        TaskListScreenSkeleton()
    }
}

@Composable
fun TaskListScreenSkeleton(
    goBack: () -> Unit = {},
    goToAddTask: (Int) -> Unit = {},
    goToEditTask: (Int) -> Unit = {},
    projectId: Int? = null,
    tasks: List<TaskUiModel> = emptyList(),
    project: Project? = null,
    saveLog: (Log) -> Unit = {}
) {
    var logTaskId by remember {
        mutableIntStateOf(-1)
    }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var showLogDialog by remember {
        mutableStateOf(false)
    }
    var totalTimeSpend by remember {
        mutableLongStateOf(0L)
    }
    Scaffold(
        topBar =
        {
            TopBar(
                onClick = { goBack() },
                leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
                title = "Today's Task",
                trailingIcon = Icons.Filled.Notifications,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (projectId != null) {
                FloatingActionButton(
                    onClick = { goToAddTask(projectId) },
                    containerColor = appColor,
                    shape = CircleShape,
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CalendarCard()
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow {
                item {
                    ProgressType(
                        onClick = { selectedIndex = 0 },
                        text = "All",
                        isSelected = selectedIndex == 0,
                    )
                }
                item {
                    ProgressType(
                        onClick = { selectedIndex = 1 },
                        text = "To do",
                        isSelected = selectedIndex == 1,
                    )
                }
                item {
                    ProgressType(
                        onClick = { selectedIndex = 2 },
                        text = "In Progress",
                        isSelected = selectedIndex == 2,
                    )
                }
                item {
                    ProgressType(
                        onClick = { selectedIndex = 3 },
                        text = "Completed",
                        isSelected = selectedIndex == 3,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(
                    when (selectedIndex) {
                        0 -> tasks
                        1 -> tasks.filter { it.taskStatus == TaskStatus.TODO }
                        2 -> tasks.filter { it.taskStatus == TaskStatus.IN_PROGRESS }
                        3 -> tasks.filter { it.taskStatus == TaskStatus.DONE }
                        else -> tasks
                    },
                ) { task ->
                    println("Total duration is ${task.totalTimeSpend}")
                    TaskCard(
                        onClick = { goToEditTask(task.id) },
                        onPauseClicked = { totalSpend ->
                            logTaskId = task.id
                            totalTimeSpend = totalSpend
                            showLogDialog = true
                        },
                        projectName = project?.name ?: "",
                        taskName = task.title,
                        duration = task.totalTimeSpend,
                        status = when (task.taskStatus) {
                            TaskStatus.TODO -> "To-do"
                            TaskStatus.IN_PROGRESS -> "In Progress"
                            TaskStatus.DONE -> "Done"
                        },
                        icon = project?.selectedIcon ?: 0,
                        iconColor = project?.selectedIconColor ?: 0,
                        backgroundColor = project?.selectedBorderColor ?: 0,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
    if (showLogDialog) {
        DataInputDialog(onDismiss = { showLogDialog = false }) { title ->
            showLogDialog = false
            saveLog(Log(taskId = logTaskId, title = title, duration = totalTimeSpend))
        }
    }
}
