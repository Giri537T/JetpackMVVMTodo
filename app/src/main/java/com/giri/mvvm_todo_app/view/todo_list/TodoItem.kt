package com.giri.mvvm_todo_app.view.todo_list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giri.mvvm_todo_app.model.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            modifier = modifier.padding(top = 15.dp),
            text = todo.todoItem,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}