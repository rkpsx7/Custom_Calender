package com.example.frnd_assignment.views

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frnd_assignment.clickListeners.OnTaskItemClickListener
import com.example.frnd_assignment.R
import com.example.frnd_assignment.models.TaskDetail
import com.example.frnd_assignment.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.alt_daig_delete.view.*

@AndroidEntryPoint
class TaskListActivity : AppCompatActivity(), OnTaskItemClickListener {

    private val viewModel: TaskViewModel by viewModels()
    private var taskList = ArrayList<TaskDetail>()
    private var taskAdapter = TaskListAdapter(taskList, this)

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

    override fun onLongClick(task: TaskDetail) {
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.alt_daig_delete, null)

        val alertDialogue = AlertDialog.Builder(this)
            .setView(view)
            .create()

        alertDialogue.show()

        view.alt_dig_yes_btn.setOnClickListener {
            viewModel.deleteTask(task)
            alertDialogue.cancel()
        }

        view.alt_dig_no_btn.setOnClickListener {
            alertDialogue.cancel()
        }


    }
}