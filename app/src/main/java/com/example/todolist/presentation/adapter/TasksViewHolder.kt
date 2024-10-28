package com.example.todolist.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

/**
 * ViewHolder для элементов списка задач.
 */
class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
}