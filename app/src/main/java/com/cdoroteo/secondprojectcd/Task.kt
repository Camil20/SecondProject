package com.cdoroteo.secondprojectcd
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasksTable")
class Task (
    @ColumnInfo(name ="title")val taskTitle:String,
    @ColumnInfo(name = "description") val taskDescription:String,
    @ColumnInfo(name = "timestamp")val timeStamp:String
    ){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}