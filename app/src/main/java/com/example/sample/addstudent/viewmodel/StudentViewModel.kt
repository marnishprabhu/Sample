package com.example.sample.addstudent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sample.repository.StudentRepository
import com.example.sample.room.entity.User
import com.example.sample.room.entity.Student

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    var student: Student? = null
    var repository = StudentRepository(application)
    var user: User? = null
    fun getAllStudents(): List<Student> {
        return repository.getAllStudents()
    }
    fun getStudentFromLiveData(): LiveData<List<Student>> {
        return repository.getStudentFromLiveData()
    }
    fun getAllStudentsForProfessorID(id:String):LiveData<List<Student>>{
        return repository.getAllStudentsForProfessorID(id.toInt())
    }
    suspend fun addStudent(student: Student) {
        this.student = student
        repository.addStudent(student)
    }
    suspend fun getOnlyFinishedStudents():List<Student>{
        return  repository.getOnlyFinishedStudents()
    }
    suspend fun updateStudent(student: Student):Int {
        this.student = student
        return repository.updateStudent(student)
    }
    fun getLastAddedStudentPrimaryKey():Int{
        return repository.getLastAddedStudentPrimaryKey()
    }
    suspend fun getStudent(id: String): Student? {
        return repository.getStudent(id)
    }
    suspend fun getStudentByUnivNumber(id:String):Student?{
        return repository.getStudentByUnivNumber(id)
    }
    suspend fun removeStudent(id:Int):Int{
        return repository.removeStudent(id)
    }
    fun getStudentUnfinishedWork():List<Student>{
        return repository.getStudentUnfinishedWork()
    }
    fun removeStudentByUniv(id:Long):Int{
        return repository.removeStudentByUniv(id)
    }
}