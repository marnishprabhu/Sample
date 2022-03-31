package com.example.sample.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sample.room.database.RoomDatabaseHelper
import com.example.sample.room.entity.Student

class StudentRepository(application: Application) {

    var roomInstance = RoomDatabaseHelper.getInstance(application)
    var studentDao = roomInstance.studentDao()

     fun getAllStudents(): List<Student> {
        return studentDao.getAllStudents()
    }
    fun getStudentFromLiveData():LiveData<List<Student>>{
        return studentDao.getStudentFromLiveData()
    }
    fun getAllStudentsForProfessorID(id:Int):LiveData<List<Student>>{
        return studentDao.getAllStudentsForProfessorID(id)
    }

    fun getOnlyFinishedStudents():List<Student>{
        return  studentDao.getOnlyFinishedStudents()
    }
    suspend fun addStudent(student: Student) {
        studentDao.addStudent(student)
    }

    suspend fun updateStudent(student: Student):Int {
        return  studentDao.updateStudent(student)
    }
    fun getLastAddedStudentPrimaryKey():Int{
        return studentDao.getLastAddedStudentPrimaryKey()
    }

    suspend fun getStudentLogin(id:String,pass:String): Student? {
       return studentDao.getStudentLogin(id,pass)
    }
    fun getStudentUnfinishedWork():List<Student>{
        return studentDao.getStudentUnfinishedWork("")
    }

    suspend fun getStudent(id:String): Student?{
        return  studentDao.getStudent(id)
    }

    suspend fun getStudentByUnivNumber(id:String):Student?{
        return studentDao.getStudentByUnivNumber(id)
    }

    fun removeStudent(id:Int):Int{
        return studentDao.removeStudent(id)
    }
    fun removeStudentByUniv(id:Long):Int{
        return studentDao.removeStudentByUniv(id)
    }

}