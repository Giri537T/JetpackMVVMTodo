package com.giri.mvvm_todo_app.model.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: TodoItemDao) : TodoRepository {
    override suspend fun insertTodoItemItem(todo: Todo) {
        dao.insertTodoItemItem(todo)
    }

    override suspend fun getAllTodoItems(): Flow<List<Todo>> {
        return dao.getAllTodoItems()
    }
}