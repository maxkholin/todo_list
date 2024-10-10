package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.model.Task

/**
 * Интерфейс для доступа к данным задач.
 * Содержит методы для работы с таблицей в базе данных.
 */
@Dao
interface TasksDao {

    /**
     * Получает список всех задач из базы данных.
     *
     * @return LiveData список задач, который обновляется при изменениях в базе данных.
     */
    @Query("SELECT * FROM tasks")
    fun getTasks(): LiveData<MutableList<Task>>

    /**
     * Добавляет новую задачу в базу данных.
     *
     * @param task Задача, которую необходимо добавить.
     */
    @Insert
    suspend fun add(task: Task)

    /**
     * Удаляет задачу из базы данных по заданному id.
     *
     * @param id id задачи, которую необходимо удалить.
     */
    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun remove(id: Int)
}
