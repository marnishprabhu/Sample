package com.example.sample.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sample.room.entity.ClassTable

@Dao
interface ClassDao {


    @Insert
    fun addClass(collegeClassTable:ClassTable)
    @Query("SELECT * FROM ClassTable")
    fun getClassData():List<ClassTable>
    @Query("SELECT * FROM ClassTable where departmentID = :depId")
    fun getAllClassesFromDepartmentId(depId:Int):List<ClassTable>

    @Query("SELECT classId FROM ClassTable where departmentID  =:depId and classYear = :year")
    fun getClassIdsForStudent(depId:Int,year:Int):List<Int>


    @Query("SELECT * FROM CLASSTABLE WHERE classId = :id")
    fun getSingleClassData(id:Int):ClassTable

    @Query("SELECT classId from ClassTable")
    fun getClassIds():List<Int>

    @Update
    fun updateProfessors(classTable: ClassTable):Int


    @Query("SELECT classId from classtable where departmentID = :deptId")
    fun getClassIdsFromDepartment(deptId:Int):List<Int>

}