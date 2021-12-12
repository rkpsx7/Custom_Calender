package com.example.frnd_assignment.models

import com.google.gson.annotations.SerializedName

data class StoreTaskRequest(
    @SerializedName("task")
    val task: TaskDetail,
    @SerializedName("user_id")
    val userId: Int
)
