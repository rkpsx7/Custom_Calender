package com.example.frnd_assignment.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frnd_assignment.R
import com.example.frnd_assignment.models.responses.TaskDetail
import kotlinx.android.synthetic.main.task_item_layout.view.*

class TaskListAdapter(private val taskList: ArrayList<TaskDetail>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(task: TaskDetail) {
        val date = task.date
        view.apply {
            tv_date_day_icon.text = "${date[0].toString() + date[1].toString()}"
            tv_task_title.text = task.taskTitle
            tv_task_desc.text = task.taskDesc
            tv_task_date_full.text = task.date
        }
    }
}