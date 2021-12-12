package com.example.frnd_assignment.clickListeners

import com.example.frnd_assignment.models.TaskDetail

interface OnTaskItemClickListener {
    fun onLongClick(task: TaskDetail)
}