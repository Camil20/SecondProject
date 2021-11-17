package com.cdoroteo.secondprojectcd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditTaskActivity : AppCompatActivity() {

    lateinit var taskTitleEdt: EditText
    lateinit var taskDescriptionEdt: EditText
    lateinit var btnAddUpdate: Button
    lateinit var viewModal: TaskViewModal
    var taskID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        taskTitleEdt = findViewById(R.id.EdtTaskTitle)
        taskDescriptionEdt = findViewById(R.id.EdtTaskDescription)
        btnAddUpdate = findViewById(R.id.btnAdd_Update)

        // initialing our view modal.
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TaskViewModal::class.java)


        val taskType = intent.getStringExtra("taskType")
        if (taskType.equals("Edit")) {
            val taskTitle = intent.getStringExtra("taskTitle")
            val taskDescription = intent.getStringExtra("taskDescription")
            taskID = intent.getIntExtra("taskID", -1)
            btnAddUpdate.text= "Update Task"
            taskTitleEdt.setText(taskTitle)
            taskDescriptionEdt.setText(taskDescription)
        } else {
            btnAddUpdate.text="Save Task"
        }

        btnAddUpdate.setOnClickListener {
            val tkTitle = taskTitleEdt.text.toString()
            val tkDescription = taskDescriptionEdt.text.toString()
            // checking the type and then saving or updating the data.
            if (taskType.equals("Edit")) {
                if (tkTitle.isNotEmpty() && tkDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateTask = Task(tkTitle, tkDescription, currentDate)
                    updateTask.id = taskID
                    viewModal.updateTask(updateTask)
                    Toast.makeText(this, "Task Updated..", Toast.LENGTH_LONG).show()
                }

            } else {
                if (tkTitle.isNotEmpty() && tkDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())

                    viewModal.addTask(Task(tkTitle, tkDescription, currentDate))
                    Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}
