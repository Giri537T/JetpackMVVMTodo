package com.giri.mvvm_todo_app.view.todo_add

sealed class AddTodoEvent {
    data class OnTodoChange(val title: String): AddTodoEvent()
    object OnSaveTodoClick: AddTodoEvent()
}
