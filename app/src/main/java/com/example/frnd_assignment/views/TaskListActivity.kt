package com.example.frnd_assignment.views

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frnd_assignment.R
import com.example.frnd_assignment.models.responses.TaskDetail
import com.example.frnd_assignment.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task_list.*

@AndroidEntryPoint
class TaskListActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels()
    private var taskList = ArrayList<TaskDetail>()
    private var taskAdapter = TaskListAdapter(taskList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_task_list)
        setAdapter()
        inflateTasks()


    }

    private fun setAdapter() {
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun inflateTasks() {
        viewModel.getTasksFromDB().observe(this, { tasks ->
            taskList.clear()
            taskList.addAll(tasks)
            taskAdapter.notifyDataSetChanged()
        })
    }
}