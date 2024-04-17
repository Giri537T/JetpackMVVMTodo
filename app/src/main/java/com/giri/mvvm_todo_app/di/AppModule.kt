package com.giri.mvvm_todo_app.di

import android.app.Application
import androidx.room.Room
import com.giri.mvvm_todo_app.model.data.TodoDatabase
import com.giri.mvvm_todo_app.model.data.TodoRepository
import com.giri.mvvm_todo_app.model.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(app, TodoDatabase::class.java,"wsa_todo_db").build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database.dao)
    }
}