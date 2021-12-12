package com.example.frnd_assignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Tasks")
data class TaskDetail(
    @SerializedName("date")
    var date: String,
    @SerializedName("task_desc")
    var taskDesc: String,
    @SerializedName("task_title")
    var taskTitle: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}