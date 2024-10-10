package com.example.todolist.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.model.Priority
import com.example.todolist.model.Task
import com.example.todolist.viewmodel.AddTaskViewModel


/**
 * Экран добавления задачи.
 * Здесь можно добавить задачу, задав текст и приоритет
 */
class AddTaskActivity : AppCompatActivity() {
    private lateinit var editTextEnterTask: EditText
    private lateinit var priorityRadioGroup: RadioGroup
    private lateinit var lowPriorityRadioButton: RadioButton
    private lateinit var mediumPriorityRadioButton: RadioButton
    private lateinit var highPriorityRadioButton: RadioButton
    private lateinit var saveButton: Button
    private lateinit var addTaskViewModel: AddTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        initViews()
        initViewModel()
        setupCloseScreenObserver()
        setupSaveButtonListener()
    }

    /**
     * Инициализирует все View.
     */
    private fun initViews() {
        editTextEnterTask = findViewById(R.id.editTextEnterTask)
        priorityRadioGroup = findViewById(R.id.priorityRadioGroup)
        lowPriorityRadioButton = findViewById(R.id.lowPriorityRadioButton)
        mediumPriorityRadioButton = findViewById(R.id.mediumPriorityRadioButton)
        highPriorityRadioButton = findViewById(R.id.highPriorityRadioButton)
        saveButton = findViewById(R.id.saveButton)
    }

    /**
     * Инициализирует ViewModel
     */
    private fun initViewModel() {
        addTaskViewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]
    }

    /**
     * Настраивает наблюдатель для закрытия экрана, если действие завершено.
     */
    private fun setupCloseScreenObserver() {
        addTaskViewModel.getShouldCloseScreen().observe(this) { shouldClose ->
            if (shouldClose) {
                finish()
            }
        }
    }

    /**
     * Сохраняет задачу, проверяя наличие текста
     */
    private fun saveTask() {
        val taskText = editTextEnterTask.text.toString().trim()
        val priority = getPriority()

        if (taskText.isBlank()) {
            showErrorMessage()
        } else {
            addTaskViewModel.saveTask(Task(text = taskText, priority = priority))
        }
    }

    /**
     * @return Возвращает приоритет задачи.
     */
    private fun getPriority(): Priority {
        return if (highPriorityRadioButton.isChecked) {
            Priority.HIGH
        } else if (mediumPriorityRadioButton.isChecked) {
            Priority.MEDIUM
        } else {
            Priority.LOW
        }
    }

    /**
     * Показывает предупрждение, если ничего не ввели.
     */
    private fun showErrorMessage() {
        Toast
            .makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT)
            .show()
    }

    /**
     * Настраивает слушатель на кнопки сохранения.
     */
    private fun setupSaveButtonListener() {
        saveButton.setOnClickListener {
            saveTask()
        }
    }


    companion object {
        /**
         * Возвращает новый Intent для запуска AddTaskActivity.
         *
         * @param context Контекст приложения.
         * @return Intent для запуска AddTaskActivity.
         */
        fun newIntent(context: Context) = Intent(context, AddTaskActivity::class.java)
    }
}