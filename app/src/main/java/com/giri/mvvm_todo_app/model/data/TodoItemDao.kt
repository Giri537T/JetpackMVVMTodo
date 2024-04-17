package com.giri.mvvm_todo_app.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface TodoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItemItem(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getAllTodoItems(): Flow<List<Todo>>


}