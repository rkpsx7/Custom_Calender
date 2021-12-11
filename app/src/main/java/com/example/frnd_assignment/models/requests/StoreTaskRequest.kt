package com.example.frnd_assignment.models.requests

import com.example.frnd_assignment.models.responses.TaskDetail
import com.google.gson.annotations.SerializedName

data class StoreTaskRequest(
    @SerializedName("task")
    val task: TaskDetail,
    @SerializedName("user_id")
    val userId: Int
)
