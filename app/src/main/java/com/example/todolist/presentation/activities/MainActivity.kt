package com.example.todolist.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.presentation.adapter.TasksAdapter
import com.example.todolist.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Основной экран приложения. Здесь отображается список задач.
 *
 * Можно удалять существующие, проводя по ним свайпом.
 *
 * Или перейти на следующий экран добавления задачи, нажав на кнопку добавления.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var buttonAddNote: FloatingActionButton
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initAndSetupAdapter()
        initViewModel()
        setupTaskListObserver()
        setupItemTouchHelper()
        setupAddTaskButtonListener()
    }

    /**
     * Инициализирует все View.
     */
    private fun initViews() {
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
        buttonAddNote = findViewById(R.id.buttonAddNote)
    }

    /**
     * Инициализирует Adapter.
     * Подключает его к RecyclerView для отображения списка задач.
     */
    private fun initAndSetupAdapter() {
        tasksAdapter = TasksAdapter()
        recyclerViewTasks.adapter = tasksAdapter
    }

    /**
     * Инициализирует ViewModel.
     */
    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    /**
     * Подписывается на обновления списка задач из ViewModel и обновляет адаптер.
     */
    private fun setupTaskListObserver() {
        mainViewModel.getTasks().observe(this) { tasks ->
            tasksAdapter.submitList(tasks )
        }
    }

    /**
     * Настраивает возможность свайпать задачи для их удаления.
     */
    private fun setupItemTouchHelper() {
        val itemTouchHelper = createItemTouchHelper()
        itemTouchHelper.attachToRecyclerView(recyclerViewTasks)
    }

    /**
     * Создаёт обработчик для свайпа задач.
     *
     * @return ItemTouchHelper для управления жестами.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {
        return ItemTouchHelper(
            object : SimpleCallback(
                0, // Не используем перетаскивание
                ItemTouchHelper.START or ItemTouchHelper.END // Разрешаем свайпы влево и вправо
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                // Удаляем задачу при свайпе
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val task = tasksAdapter.currentList[position]
                    mainViewModel.remove(task)
                }
            }
        )
    }

    /**
     * Добавляет слушатель для кнопки добавления задачи.
     * При нажатии запускается экран для создания новой задачи.
     */
    private fun setupAddTaskButtonListener() {
        buttonAddNote.setOnClickListener {
            startActivity(AddTaskActivity.newIntent(this))
        }
    }
}
