package com.giri.mvvm_todo_app.model.data
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insertTodoItemItem(todo: Todo)
    suspend fun getAllTodoItems(): Flow<List<Todo>>


}