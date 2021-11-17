package com.cdoroteo.secondprojectcd
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task : Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("Select * from tasksTable order by id ASC ")
    fun getAllTasks(): LiveData<List<Task>>
}
