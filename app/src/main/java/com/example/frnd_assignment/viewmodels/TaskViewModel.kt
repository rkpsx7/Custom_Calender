package com.example.frnd_assignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.frnd_assignment.models.TaskDetail
import com.example.frnd_assignment.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repo: TaskRepository) : ViewModel() {

    fun getTasksFromServer() {
        return repo.getTasksFromServer()
    }

    fun getNoOfTasks(date: String): LiveData<Int> {
        return repo.getNoOfTasks(date)
    }

    fun getTasksFromDB(): LiveData<List<TaskDetail>> {
        return repo.getTasksFromDB()
    }

    fun storeTaskOnServer(taskObj: TaskDetail) {
        repo.storeTask(taskObj)
    }


    fun deleteTask(taskObj: TaskDetail) {
        repo.deleteTask(taskObj)
    }

}