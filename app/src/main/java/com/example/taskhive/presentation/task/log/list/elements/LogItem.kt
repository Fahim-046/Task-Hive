package com.example.taskhive.presentation.task.log.list.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskhive.components.TaskGroupIcon
import com.example.taskhive.utils.formatLogTime

@Composable
fun LogItem(modifier: Modifier = Modifier, isFirst:Boolean, title: String, duration: Long, taskId:Int = 0) {
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (!isFirst) {
                Box(
                    modifier = modifier
                        .width(1.dp)
                        .height(50.dp)
                        .background(color = Color(0xFFC9C7C7))
                )
            }
            TaskGroupIcon()
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            Text(
                text = formatLogTime(duration),
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 1
            )
            Text(title, maxLines = 1, modifier = Modifier.padding(top = 4.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun LogItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LogItem(isFirst = true, title = "First portion of task", duration = 10)
        LogItem(isFirst = false, title = "Second portion of task", duration = 12)
    }
}