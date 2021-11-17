package com.cdoroteo.secondprojectcd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cdoroteo.secondprojectcd.databinding.ActivityTaskBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskActivity : AppCompatActivity(), TaskClickInterface, TaskClickDeleteInterface {

    lateinit var binding: ActivityTaskBinding
    lateinit var taskRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModal: TaskViewModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_task)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_task)
        taskRV.layoutManager = LinearLayoutManager(this)

        val taskRVAdapter = TaskRvAdapter(this, this, this)
        taskRV.adapter = taskRVAdapter
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModal::class.java)
        viewModal.allTasks.observe(this, {list ->
            list?.let{
                taskRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener{
            val intent = Intent(this@TaskActivity, AddEditTaskActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onTaskClick(task: Task) {
        val intent = Intent(this@TaskActivity,AddEditTaskActivity::class.java)
        intent.putExtra("TaskType","Edit")
        intent.putExtra("TaskTitle",task.taskTitle)
        intent.putExtra("TaskDescription",task.taskDescription)
        intent.putExtra("TaskID",task.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(task: Task) {
        viewModal.deleteTask(task)
        Toast.makeText(this, "${task.taskTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}