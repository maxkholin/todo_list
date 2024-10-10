package com.example.todolist.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.launch

private const val TAG = "AddTaskViewModel"

/**
 * ViewModel для добавления задач в приложении.
 *
 * Обеспечивает взаимодействие с базой данных и обновление состояния экрана.
 *
 * Сохранение новой задачи происходит в фоновом потоке.
 */
class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDatabase = TaskDatabase.getInstance(application)
    private var shouldCloseScreen = MutableLiveData<Boolean>()

    /**
     * Получает состояние закрытия экрана.
     *
     * @return LiveData, тип Boolean, нужно ли закрыть экран.
     */
    fun getShouldCloseScreen(): LiveData<Boolean> {
        return shouldCloseScreen
    }

    /**
     * Сохраняет задачу в базе данных и устанавливает состояние для закрытия экрана.
     * Сохранение происходит в фоновом потоке.
     *
     * @param task Задача, которую необходимо сохранить.
     */
    fun saveTask(task: Task) {
        viewModelScope.launch {
            try {
                taskDatabase.taskDao().add(task)
                shouldCloseScreen.value = true
            } catch (e: Exception) {
                shouldCloseScreen.value = false
                Log.e(TAG, "Error saving task", e)
            }
            Log.d(TAG, "Save task done")
        }
    }
}