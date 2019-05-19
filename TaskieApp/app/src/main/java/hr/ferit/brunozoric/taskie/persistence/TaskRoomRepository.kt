package hr.ferit.brunozoric.taskie.persistence

import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.db.DaoProvider
import hr.ferit.brunozoric.taskie.db.TaskDao
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

class TaskRoomRepository: TaskRepository  {

    private var db: DaoProvider = DaoProvider.getInstance(Taskie.getAppContext())
    private var taskDao: TaskDao = db.taskDao()

    override fun addTask(task: Task) {
        taskDao.insertTask(task)
     }

    override fun getTasks(): MutableList<Task> {
        return taskDao.loadAll()
    }

    override fun getTaskBy(id: Int): Task {
        return taskDao.getTask(id)
    }

    override fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun clearAllTasks() {
        taskDao.deleteAllTasks()
    }

    override fun editTask(task: Task, title: String, description: String, priority: Priority) {
        taskDao.changeTaskTitle(task.taskDbId!!,title)
        taskDao.changeTaskDescription(task.taskDbId!!, description)
        taskDao.changeTaskPriority(task.taskDbId!!,priority)
    }

    override fun sortTasks(): List<Task> {
        return taskDao.getTasksOrderedByPriority()
    }
}