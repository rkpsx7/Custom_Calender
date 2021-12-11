package com.example.frnd_assignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.frnd_assignment.models.requests.StoreTaskRequest
import com.example.frnd_assignment.models.responses.StatusResponse
import com.example.frnd_assignment.models.responses.TaskDetail
import com.example.frnd_assignment.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repo: TaskRepository) : ViewModel() {

    fun getTasksFromServer() {
        return repo.getTasksFromServer()
    }

    fun getTasksFromDB(): LiveData<List<TaskDetail>> {
        return repo.getTasksFromDB()
    }

    fun storeTask(taskObj: TaskDetail) {
        repo.storeTask(taskObj)
    }


    suspend fun deleteTaskFromServer(deleteReq: HashMap<String, Int>): StatusResponse {
        return repo.deleteTaskFromServer(deleteReq)
    }

}