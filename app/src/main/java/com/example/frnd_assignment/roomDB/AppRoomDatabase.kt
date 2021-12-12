package com.example.frnd_assignment.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.frnd_assignment.models.TaskDetail

@Database(entities = [TaskDetail::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}