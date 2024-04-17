import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.giri.mvvm_todo_app.util.UiEvent
import com.giri.mvvm_todo_app.view.todo_list.TodoItem
import com.giri.mvvm_todo_app.view.todo_list.TodoListEvent
import com.giri.mvvm_todo_app.viewmodel.TodoListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    data: String,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val focusmanager = LocalFocusManager.current
    val todos = viewModel.todosList?.collectAsState(initial = emptyList())
    var openErrorPopup by remember { mutableStateOf(false) }
      openErrorPopup = data == "showError"


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onTodoEvent(event = TodoListEvent.AddTodoClickEvent) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }) {
        if (todos?.value?.isEmpty() == true) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Press the + button to add a TODO item",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500)
                )
            }

        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //search box
                OutlinedTextField(
                    value = searchText, onValueChange = { searchText = it },
                    placeholder = { Text(text = "search TODO here") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusmanager.clearFocus()
                    }),

                    modifier = Modifier
                        .fillMaxWidth()

                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    val filteredTodos = todos?.value?.filter {
                        it.todoItem.contains(searchText.text, ignoreCase = true)
                    } ?: emptyList()
                    items(filteredTodos) {
                        TodoItem(
                            todo = it, modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
            if (openErrorPopup){
                ErrorPopup(message = "Failed to add TODO") {}
            }
        }

      

    }
}
    @Composable
    fun ErrorPopup(message: String, onClose: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onClose()},
            title = {
                Text(text = "Error")
            },
            text = {
                Text(text = message, textAlign = TextAlign.Center)
            },
            confirmButton = {
                Button(onClick = { onClose() }) {
                    Text(text = "OK")
                }
            },
            modifier = Modifier.size(250.dp, 250.dp),
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        )
    }
