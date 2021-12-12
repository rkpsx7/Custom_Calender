package com.example.frnd_assignment.models

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("status")
    val status: String
)
