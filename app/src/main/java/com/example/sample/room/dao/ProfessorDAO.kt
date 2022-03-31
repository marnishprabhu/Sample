package com.example.sample.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student


@Dao
interface ProfessorDAO {


    @Query("SELECT * FROM Professor")
    fun getProfessorsLiveData():LiveData<List<Professor>>

    @Query("SELECT * FROM Professor WHERE professorID =:id and password = :pass")
    fun getProfessorLogin(id:Long,pass:String): Professor?



    @Query("SELECT * FROM Professor")
    fun getAllProfessors():List<Professor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProfessor(professor: Professor):Long
    @Update
    fun updateProfessor(professor: Professor):Int

    @Query("SELECT * FROM Professor WHERE professorID = :id")
    fun getProfessor(id:Int): Professor?
    @Query("SELECT * FROM PROFESSOR WHERE password!= 'null' and password!=''")
    fun getOnlyFinishedProfessors():List<Professor>

    @Query("DELETE FROM PROFESSOR WHERE professorID = :id")
    fun removeProfessor(id:Int):Int

    @Query("SELECT * FROM PROFESSOR WHERE password = :empty or password is NULL")
    fun getProfessorUnfinishedWork(empty:String):List<Professor>
}