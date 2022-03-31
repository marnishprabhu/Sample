package com.example.sample.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.sample.room.entity.Department


@Dao
interface DepartmentDao {


    @Query("SELECT * FROM DEPARTMENT")
    fun getDepartments():List<Department>
    @Query("SELECT * FROM DEPARTMENT WHERE deparmentID = :id")
    fun getDepartmentByID(id:Int):Department

    @Query("SELECT * FROM DEPARTMENT WHERE departmentName = :name")
    fun getDepartmentByName(name:String):Department

    @Insert(onConflict = IGNORE)
    fun addDepartment(depart:Department)
}