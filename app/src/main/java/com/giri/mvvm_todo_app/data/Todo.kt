package com.giri.mvvm_todo_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey
    val id: Int? = null,
    val todoItem: String?
)
