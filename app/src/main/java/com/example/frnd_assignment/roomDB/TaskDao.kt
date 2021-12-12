package com.example.frnd_assignment.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.frnd_assignment.models.TaskDetail

@Dao
interface TaskDao {

    @Insert(onConflict = REPLACE)
    fun insertTask(taskObj: TaskDetail)

    @Query("select * from tasks")
    fun getTasksFromDB(): LiveData<List<TaskDetail>>

    @Delete
    fun deleteTask(taskObj: TaskDetail)

    @Query("delete from tasks")
    fun deleteAll()

    @Query("select COUNT(*) from tasks where date =:date")
    fun getNoOfTasks(date: String):LiveData<Int>
}