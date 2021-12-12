package com.example.frnd_assignment.repositories

import androidx.lifecycle.LiveData
import com.example.frnd_assignment.models.StoreTaskRequest
import com.example.frnd_assignment.models.TaskDetail
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
            val userID = hashMapOf<String, Int>()
            userID["user_id"] = userId
            val response = apiService.getTasksFromServer(userID).tasks
            dao.deleteAll()
            for (i in response.indices) {
                val taskObj = response[i]
                val task = taskObj.taskDetail
                task.id = taskObj.taskId
                insertTaskToDB(task)
            }
        }
    }
    fun getNoOfTasks(date: String):LiveData<Int>{
       return dao.getNoOfTasks(date)
    }

    fun storeTask(taskObj: TaskDetail) {
        CoroutineScope(IO).launch {
            val reqObj = StoreTaskRequest(taskObj, userId)
            val response = apiService.storeTaskOnServer(reqObj)
            if (response.status == "Success")
                getTasksFromServer()
        }

    }

    fun deleteTask(taskObj: TaskDetail) {
        CoroutineScope(IO).launch {
            dao.deleteTask(taskObj)
            val delReq = hashMapOf<String, Int>()
            delReq["user_id"] = userId
            delReq["task_id"] = taskObj.id!!.toInt()
            apiService.deleteTaskFromServer(delReq)
        }
    }

    fun getTasksFromDB(): LiveData<List<TaskDetail>> {
        return dao.getTasksFromDB()
    }

    private fun insertTaskToDB(taskObj: TaskDetail) {
        dao.insertTask(taskObj)
    }

    fun deleteTaskFromDB(taskObj: TaskDetail) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteTask(taskObj)
        }
    }
}