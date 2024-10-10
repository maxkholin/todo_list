package com.example.todolist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.Priority
import com.example.todolist.model.Task

/**
 * Адаптер для отображения списка задач в RecyclerView.
 * Управляет отображением задач, их приоритетами и обработкой кликов.
 */
class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    private var _tasks = mutableListOf<Task>()

    /**
     * Получает или устанавливает список задач.
     * При установке нового списка уведомляет адаптер об изменениях.
     */
    var tasks: MutableList<Task>
        get() = _tasks
        set(value) {
            _tasks = value
            notifyDataSetChanged()
        }

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
     * Возвращает количество задач в списке.
     */
    override fun getItemCount(): Int = tasks.size

    /**
     * Связывает данные задачи с элементами пользовательского интерфейса.
     */
    override fun onBindViewHolder(taskViewHolder: TasksViewHolder, position: Int) {
        val task = tasks[position]
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

    /**
     * ViewHolder для элементов списка задач.
     */
    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
    }
}