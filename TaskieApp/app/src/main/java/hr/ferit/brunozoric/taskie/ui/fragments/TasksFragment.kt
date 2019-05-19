package hr.ferit.brunozoric.taskie.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.*
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepository
import hr.ferit.brunozoric.taskie.ui.activities.ContainerActivity
import hr.ferit.brunozoric.taskie.ui.adapters.TaskAdapter
import hr.ferit.brunozoric.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {

    private val repository = TaskRoomRepository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initUi()
        initListeners()
        refreshTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_and_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sortItem -> sortByPriority()
            R.id.deleteAllItem -> deleteAllTasks()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTasks() {
        progress.gone()
        if(adapter.itemCount==0)
            context?.displayToast(getString(R.string.noTasks))
        else {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.alertDeleteAllTasksTitle))
            builder.setMessage(getString(R.string.alertDeleteAllTasks))
            builder.setPositiveButton(getString(R.string.alertPositive)) { dialog, _ ->
                dialog.cancel()
                repository.clearAllTasks()
                val data: MutableList<Task> = mutableListOf()
                adapter.setData(data)
                refreshTasks()
            }
            builder.setNegativeButton(getString(R.string.alertNegative)) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    private fun sortByPriority() {
        progress.gone()
        val data = repository.sortTasks()
        if(data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(data.toMutableList())
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter

        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val builder = AlertDialog.Builder(viewHolder.itemView.context)
                builder.setTitle(getString(R.string.alertDeleteTaskTitle))
                builder.setMessage(getString(R.string.alertDeleteTask))
                builder.setPositiveButton(getString(R.string.alertPositive)){dialog, _ -> dialog.cancel()
                    repository.deleteTask(adapter.getTaskAt(position))
                adapter.removeAt(position)
                if(adapter.itemCount==0)
                    refreshTasks()}
                builder.setNegativeButton(getString(R.string.alertNegative)){dialog, _ -> dialog.cancel()
                adapter.notifyItemChanged(position)}
                builder.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun onItemSelected(task: Task){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.taskDbId)
        }
        startActivity(detailsIntent)
    }



    private fun refreshTasks() {
        progress.gone()
        val data = repository.getTasks()
        if (data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(data)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}