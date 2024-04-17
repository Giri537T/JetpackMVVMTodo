package com.giri.mvvm_todo_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giri.mvvm_todo_app.model.data.Todo
import com.giri.mvvm_todo_app.model.data.TodoRepository
import com.giri.mvvm_todo_app.util.UiEvent
import com.giri.mvvm_todo_app.view.todo_add.AddTodoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

    var todoText by mutableStateOf("")
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddTodoEvent) {
        when (event) {
            is AddTodoEvent.OnTodoChange -> {
                todoText = event.title
            }

            is AddTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (todoText.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "TODO cannot be blank!!"
                            )
                        )
                        return@launch
                    } else if (todoText == "Error") {
                        // As per the assignment we have to create exception
                        try {
                            throwError()
                        } catch (e: Exception) {
                            sendUiEvent(UiEvent.showErrorPopUp())
                        }
                    } else {
                        repository.insertTodoItemItem(
                            Todo(
                                todoItem = todoText,
                                isDone = todo?.isDone ?: false,
                                id = todo?.id
                            )
                        )
                        sendUiEvent(UiEvent.PopBackStack)

                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun throwError() {
        throw IOException()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}