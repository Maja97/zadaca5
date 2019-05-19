package hr.ferit.brunozoric.taskie.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment

import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.common.displayToast
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.brunozoric.taskie.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_edit_task.*


class EditTaskFragmentDialog(var taskId: Int) : DialogFragment() {

    private val repository = TaskRoomRepository()
    private var taskEditedListener: TaskEditedListener? = null
    lateinit var task: Task

    interface TaskEditedListener {
        fun onTaskEdited(task: Task)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    fun setTaskEditedListener(listener: TaskEditedListener){
        taskEditedListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_edit_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initUi(){
        task = repository.getTaskBy(taskId)

        context?.let {
            editPrioritySelector.adapter = ArrayAdapter<Priority>(it, android.R.layout.simple_spinner_dropdown_item, Priority.values())
            editPrioritySelector.setSelection(0)
        }
        editTaskTitleInput.setText(task.title)
        editTaskDescriptionInput.setText(task.description)
    }

    private fun initListeners(){
        editSaveTaskAction.setOnClickListener{ editTask() }
    }

    private fun editTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = editTaskTitleInput.text.toString()
        val description = editTaskDescriptionInput.text.toString()
        val priority = editPrioritySelector.selectedItem as Priority

        repository.editTask(task,title,description,priority)

        clearUi()

        taskEditedListener?.onTaskEdited(task)
        dismiss()
    }


    private fun clearUi() {
        editTaskTitleInput.text.clear()
        editTaskDescriptionInput.text.clear()
        editPrioritySelector.setSelection(0)
    }


    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(editTaskTitleInput.text) || TextUtils.isEmpty(
        editTaskDescriptionInput.text
    )

    companion object{
        fun newInstance(taskId: Int)= EditTaskFragmentDialog (taskId)
    }
}
