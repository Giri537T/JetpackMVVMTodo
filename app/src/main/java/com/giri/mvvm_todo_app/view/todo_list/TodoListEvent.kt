package com.giri.mvvm_todo_app.view.todo_list

import com.giri.mvvm_todo_app.model.data.Todo

/**
 * This class is used to get the what kind of UI interactions we will do for the TODO_ITEM
 * EX: clicked on floating action button, or individual todoItem click etc..
 */
sealed class TodoListEvent {

    // Floating action Event
     object AddTodoClickEvent: TodoListEvent()

    // On Done TODO Event
    data class OnDoneChange(val todo: Todo,val  isDone:Boolean): TodoListEvent()
}