package com.giri.mvvm_todo_app.view

import TodoListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.giri.mvvm_todo_app.util.Routes
import com.giri.mvvm_todo_app.view.theme.MVVM_Todo_AppTheme
import com.giri.mvvm_todo_app.view.todo_add.AddTodoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVM_Todo_AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TODO_LIST_SCREEN
                ) {
                    composable(Routes.TODO_LIST_SCREEN) {
                        TodoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            ""
                        )
                    }
                    composable(Routes.ADD_TODO_SCREEN ){
                        AddTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })

                    }


                }
            }
        }
    }
}
