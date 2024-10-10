package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

/**
 * Модель данных Задачи.
 * Содержит идентификатор, текст задачи и приоритет.
 */
@Entity(tableName = "tasks")
data class Task(
    // Уникальный идентификатор задачи, генерируется автоматически
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val priority: Priority
)

/**
 * Перечисление, представляющее приоритет задачи.
 * Содержит три уровня приоритета: НИЗКИЙ, СРЕДНИЙ и ВЫСОКИЙ.
 */
enum class Priority {
    LOW, MEDIUM, HIGH
}

/**
 * Конвертер для преобразования приоритета задачи в строку и обратно.
 * Используется Room для хранения приоритета в базе данных.
 */
class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(name: String): Priority {
        return Priority.valueOf(name)
    }
}