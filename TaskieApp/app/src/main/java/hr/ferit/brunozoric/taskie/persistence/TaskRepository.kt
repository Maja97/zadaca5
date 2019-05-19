package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

interface TaskRepository {

    fun addTask(task: Task)
    fun getTasks(): List<Task>
    fun getTaskBy(id: Int): Task
    fun deleteTask(task: Task)
    fun clearAllTasks()
    fun editTask(task: Task, title: String, description: String, priority: Priority)
    fun sortTasks(): List<Task>
}
