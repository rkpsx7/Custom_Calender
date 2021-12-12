package com.example.frnd_assignment.remote

import com.example.frnd_assignment.models.StoreTaskRequest
import com.example.frnd_assignment.models.StatusResponse
import com.example.frnd_assignment.models.TaskResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/getCalendarTaskLists")
    suspend fun getTasksFromServer(@Body user_id: HashMap<String, Int>): TaskResponse

    @POST("/api/storeCalendarTask")
    suspend fun storeTaskOnServer(@Body taskObj: StoreTaskRequest): StatusResponse

    @POST("/api/deleteCalendarTask")
    suspend fun deleteTaskFromServer(@Body deleteReq: HashMap<String, Int>): StatusResponse

}