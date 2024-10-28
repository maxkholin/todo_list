package com.example.todolist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.R
import com.example.todolist.model.Priority
import com.example.todolist.model.Task

/**
 * Адаптер для отображения списка задач в RecyclerView.
 * Управляет отображением задач, их приоритетами и обработкой кликов.
 */
class TasksAdapter : ListAdapter<Task, TasksViewHolder>(TaskDiffCallback()) {

    /**
     * Создаёт новый ViewHolder для элемента списка.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return TasksViewHolder(view)
    }

    /**
     * Связывает данные задачи с элементами пользовательского интерфейса.
     */
    override fun onBindViewHolder(taskViewHolder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        bindTask(taskViewHolder, task)
    }

    /**
     * Привязывает данные задачи к ViewHolder.
     */
    private fun bindTask(taskViewHolder: TasksViewHolder, task: Task) {
        taskViewHolder.textViewTask.apply {
            text = task.text
            setBackgroundColor(getPriorityColor(task.priority, taskViewHolder.itemView))
        }
    }

    /**
     * Получает цвет фона для приоритета задачи.
     */
    private fun getPriorityColor(priority: Priority, view: View): Int {
        return when (priority) {
            Priority.LOW -> ContextCompat
                .getColor(view.context, android.R.color.holo_green_light)

            Priority.MEDIUM -> ContextCompat
                .getColor(view.context, android.R.color.holo_orange_light)

            Priority.HIGH -> ContextCompat
                .getColor(view.context, android.R.color.holo_red_light)
        }
    }

}