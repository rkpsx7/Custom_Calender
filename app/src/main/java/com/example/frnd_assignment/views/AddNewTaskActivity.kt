package com.example.frnd_assignment.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.frnd_assignment.R
import com.example.frnd_assignment.models.responses.TaskDetail
import com.example.frnd_assignment.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_new_task.*

@AndroidEntryPoint
class AddNewTaskActivity : AppCompatActivity() {
    private val viewModel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        val date = intent.getStringExtra("date")
        et_new_task_date.setText(date)
        btn_add_task.setOnClickListener {
            val taskTitle = et_new_task_title.text
            val taskDesc = et_new_task_desc.text
            val task = TaskDetail(date.toString(), taskDesc.toString(), taskTitle.toString())
            viewModel.storeTask(task)
            onBackPressed()
        }

    }
}