package com.example.frnd_assignment.models.responses


import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("tasks")
    val tasks: List<Task>
)