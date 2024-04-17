package com.giri.mvvm_todo_app.view.todo_add

import TodoListScreen
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.giri.mvvm_todo_app.util.UiEvent
import com.giri.mvvm_todo_app.viewmodel.AddTodoViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddTodoViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val navController = rememberNavController()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                          message = event.message,
                          actionLabel = event.action
                      )
                }
                is UiEvent.showErrorPopUp -> {
                    showError = true
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.todoText,
                onValueChange = {
                    viewModel.onEvent(AddTodoEvent.OnTodoChange(it))
                },
                placeholder = {
                    Text(text = "Enter Todo Here")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                    if (viewModel.todoText == "Error" || viewModel.todoText.isNullOrBlank()){
                        viewModel.onEvent(AddTodoEvent.OnSaveTodoClick)
                    }else {
                        isLoading = true
                }
            })
            {
                Text("Add TODO")
            }
        }
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator(onLoadingFinished = {
                    viewModel.onEvent(AddTodoEvent.OnSaveTodoClick)
                })
            }

        }
        if (showError) {
                TodoListScreen(
                    onNavigate = {
                        navController.navigate(it.route)
                    },
                    "showError"
                )
        }
    }

}

@Composable
fun LoadingIndicator(
    durationMillis: Long = 3000, // Default duration is 3 seconds
    onLoadingFinished: () -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        isLoading = false
        onLoadingFinished()
    }

    if (isLoading) {
        // Show your loading indicator here
        // For example, you can use CircularProgressIndicator
        CircularProgressIndicator()
    }
}
