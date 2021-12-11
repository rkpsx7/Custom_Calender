package com.example.frnd_assignment.repositories

import androidx.lifecycle.LiveData
import com.example.frnd_assignment.models.requests.StoreTaskRequest
import com.example.frnd_assignment.models.responses.StatusResponse
import com.example.frnd_assignment.models.responses.TaskDetail
import com.example.frnd_assignment.remote.ApiService
import com.example.frnd_assignment.roomDB.TaskDao
import com.example.frnd_assignment.utils.Constants.userId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: TaskDao
) {
    fun getTasksFromServer() {
        CoroutineScope(IO).launch {
            val userID = hashMapOf<String,Int>()
            userID["user_id"] = userId
            val response = apiService.getTasksFromServer(userID).tasks

            for (i in response.indices) {
                val taskObj = response[i]
                val task = taskObj.taskDetail
                task.id = taskObj.taskId
                insertTaskToDB(task)
            }

        }
    }

    fun storeTask(taskObj: TaskDetail) {
        CoroutineScope(IO).launch {
            dao.insertTask(taskObj)
            val reqObj = StoreTaskRequest(taskObj, userId)
            apiService.storeTaskOnServer(reqObj)
        }

    }

    suspend fun deleteTaskFromServer(deleteReq: HashMap<String, Int>): StatusResponse {
        return apiService.deleteTaskFromServer(deleteReq)
    }

    fun getTasksFromDB(): LiveData<List<TaskDetail>> {
        return dao.getTasksFromDB()
    }

    private fun insertTaskToDB(taskObj: TaskDetail) {
        dao.deleteAll()
        dao.insertTask(taskObj)
    }

    fun deleteTaskFromDB(taskObj: TaskDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteTask(taskObj)
        }
    }
}