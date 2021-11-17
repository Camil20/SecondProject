package com.cdoroteo.secondprojectcd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cdoroteo.secondprojectcd.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(),  TaskClickInterface, TaskClickDeleteInterface {

    lateinit var binding: ActivityMainBinding
    lateinit var taskRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModal: TaskViewModal


    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        taskRV = binding.RvTaskID
        addFAB = binding.btnAddTask

        taskRV.layoutManager = LinearLayoutManager(this)

        val taskRVAdapter = TaskRvAdapter(this, this, this)
        taskRV.adapter = taskRVAdapter
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TaskViewModal::class.java)
        viewModal.allTasks.observe(this, Observer{list ->
            list?.let{
                taskRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener{
            val intent = Intent(this@MainActivity, AddEditTaskActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        //binding.tabs.addOnTabSelectedListener(this)

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        //navController = navHostFragment.navController

        //NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onDeleteIconClick(task: Task) {
        viewModal.deleteTask(task)
        Toast.makeText(this, "${task.taskTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onTaskClick(task: Task) {
        val intent = Intent(this@MainActivity,AddEditTaskActivity::class.java)
        intent.putExtra("taskType","Edit")
        intent.putExtra("taskTitle",task.taskTitle)
        intent.putExtra("taskDescription",task.taskDescription)
        intent.putExtra("taskID",task.id)
        startActivity(intent)
        this.finish()
    }

    //override fun onSupportNavigateUp(): Boolean {
       // return navController.navigateUp()
    //}

    //override fun onTabSelected(tab: TabLayout.Tab?) {
       // when (tab?.position){
         //   0 -> navController.navigate(R.id.profileFragment)
         //   1 -> navController.navigate(R.id.taskFragment)
       // }
   // }


}