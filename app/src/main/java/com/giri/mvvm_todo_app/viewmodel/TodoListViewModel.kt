package com.giri.mvvm_todo_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giri.mvvm_todo_app.model.data.Todo
import com.giri.mvvm_todo_app.model.data.TodoRepository
import com.giri.mvvm_todo_app.util.Routes
import com.giri.mvvm_todo_app.util.UiEvent
import com.giri.mvvm_todo_app.view.todo_list.TodoListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) :
    ViewModel() {

    var todosList: Flow<List<Todo>>? = null
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            todosList = todoRepository.getAllTodoItems()
        }
    }

    fun onTodoEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.AddTodoClickEvent -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_TODO_SCREEN))
                }
            }

            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    todoRepository.insertTodoItemItem(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
