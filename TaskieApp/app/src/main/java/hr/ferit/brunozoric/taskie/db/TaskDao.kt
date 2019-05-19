package hr.ferit.brunozoric.taskie.db

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun loadAll(): MutableList<Task>

    @Query("SELECT * FROM Task WHERE TaskDbId = :taskId")
    fun getTask(taskId: Int): Task

    @Insert(onConflict = IGNORE)
    fun insertTask(task: Task): Long

    @Update(onConflict = REPLACE)
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM Task")
    fun deleteAllTasks()

    @Query("UPDATE task SET title = :taskTitle WHERE taskDbId = :taskId")
    fun changeTaskTitle(taskId: Int,taskTitle: String)

    @Query("UPDATE task SET description = :taskDescription WHERE taskDbId = :taskId")
    fun changeTaskDescription(taskId: Int,taskDescription: String)

    @Query("UPDATE task SET priority = :taskPriority WHERE taskDbId = :taskId")
    fun changeTaskPriority(taskId: Int,taskPriority: Priority)

    @Query("SELECT * FROM task ORDER BY priority DESC")
    fun getTasksOrderedByPriority(): List<Task>

}