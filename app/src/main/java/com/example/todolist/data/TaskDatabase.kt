package com.example.todolist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.model.PriorityConverter
import com.example.todolist.model.Task

/**
 * База данных Room для хранения задач.
 *
 * Содержит определение сущностей и настройку базы данных.
 */
@Database(entities = [Task::class], version = 1)
@TypeConverters(PriorityConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    companion object {
        private var instance: TaskDatabase? = null
        private const val DB_NAME = "tasks.db"

        /**
         * Получает единственный экземпляр базы данных.
         * Если экземпляр еще не создан, создает его.
         *
         * @param application Приложение, использующее базу данных.
         * @return Экземпляр базы данных.
         */
        fun getInstance(application: Application): TaskDatabase {
            if (instance == null) {
                synchronized(TaskDatabase::class) {
                    instance = buildRoomDB(application)
                }
            }
            return instance!!
        }

        /**
         * Создает базу данных с использованием Room.
         *
         * @param application Приложение, использующее базу данных.
         * @return Экземпляр базы данных.
         */
        private fun buildRoomDB(application: Application) =
            Room.databaseBuilder(
                application.applicationContext,
                TaskDatabase::class.java,
                DB_NAME
            ).build()
    }

    /**
     * Получает интерфейс для доступа к данным.
     *
     * @return TasksDao объект для работы с задачами.
     */
    abstract fun taskDao(): TasksDao
}
