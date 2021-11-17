package com.cdoroteo.secondprojectcd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView


class TaskRvAdapter(
    val context: Context,
    val taskClickInterface: TaskClickInterface,
    val taskClickDeleteInterface: TaskClickDeleteInterface
    ) : RecyclerView.Adapter<TaskRvAdapter.ViewHolder>() {

        private val allTask = ArrayList<Task>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val taskTv = itemView.findViewById<TextView>(R.id.txtTaskTitleId)
            val timeTv = itemView.findViewById<TextView>(R.id.txtTaskDescriptionId)
            val deleteImg = itemView.findViewById<ImageView>(R.id.imgVDelete)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskTv.setText(allTask.get(position).taskTitle)
        holder.timeTv.setText("Last Updated: " + allTask.get(position).timeStamp)

        holder.deleteImg.setOnClickListener{
            taskClickDeleteInterface.onDeleteIconClick(allTask.get(position))
        }

        holder.itemView.setOnClickListener{
            taskClickInterface.onTaskClick(allTask.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allTask.size
    }

    fun updateList(newList : List<Task>){
        allTask.clear()
        allTask.addAll(newList)
        notifyDataSetChanged()
    }
}

interface TaskClickDeleteInterface{
    fun onDeleteIconClick(task: Task)
}

interface TaskClickInterface{
    fun onTaskClick(task: Task)
}