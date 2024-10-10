package com.example.todolist.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.launch


private const val TAG = "MainViewModel"

/**
 * ViewModel для удаления и получения задач в приложении.
 *
 * Обеспечивает взаимодействие с базой данных и хранение состояния.
 *
 * Удаление происходит в фоновом потоке.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDatabase = TaskDatabase.getInstance(application)

    /**
     * Удаляет задачу из базы данных.
     * Удаление происходит в фоновом потоке.
     *
     * @param task Задача, которую необходимо удалить.
     */
    fun remove(task: Task) {
        viewModelScope.launch {
            try {
                taskDatabase.taskDao().remove(task.id)
            } catch (e: Exception) {
                Log.e(TAG, "Error saving task", e)
            }
        }
    }

    /**
     * Получает список задач из базы данных.
     *
     * @return LiveData, содержащая список задач.
     */
    fun getTasks(): LiveData<MutableList<Task>> {
        return taskDatabase.taskDao().getTasks()
    }
}