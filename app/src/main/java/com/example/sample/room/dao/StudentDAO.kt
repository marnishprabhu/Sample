package com.example.sample.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.sample.room.entity.Student


@Dao
interface StudentDAO {
    @Query("SELECT * FROM Student WHERE clg_num =:id and password = :pass")
    fun getStudentLogin(id:String,pass:String):Student?


    @Query("SELECT * FROM Student")
    fun getStudentFromLiveData():LiveData<List<Student>>

    @Query("SELECT * FROM STUDENT WHERE clg_num!= 'null' and clg_num!=''")
    fun getOnlyFinishedStudents():List<Student>


    @Query("SELECT * FROM Student")
    fun getAllStudents():List<Student>


    @Query("SELECT * FROM STUDENT WHERE professorID = :id")
    fun getAllStudentsForProfessorID(id:Int):LiveData<List<Student>>

    @Insert(onConflict = REPLACE)
    fun addStudent(student: Student)
    @Update
    fun updateStudent(student: Student):Int

    @Query("SELECT * FROM STUDENT WHERE clg_num = :id")
    fun getStudent(id:String): Student?

    @Query("DELETE FROM STUDENT WHERE clg_num = :id")
    fun removeStudent(id:Int):Int

    @Query("SELECT * FROM STUDENT WHERE univ_num = :id")
    fun getStudentByUnivNumber(id:String):Student?

    @Query("DELETE FROM STUDENT WHERE univ_num = :id")
    fun removeStudentByUniv(id:Long):Int

    @Query("SELECT id FROM Student ORDER BY id DESC LIMIT 1")
    fun getLastAddedStudentPrimaryKey():Int


    @Query("SELECT * FROM STUDENT WHERE clg_num = :empty")
    fun getStudentUnfinishedWork(empty:String):List<Student>
}