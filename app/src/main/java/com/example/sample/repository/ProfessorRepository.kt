package com.example.sample.repository

import android.app.Application
import androidx.lifecycle.LiveData

import com.example.sample.room.entity.Professor
import com.example.sample.room.database.RoomDatabaseHelper
import com.example.sample.room.entity.Student

class ProfessorRepository(application: Application) {
    var roomInstance = RoomDatabaseHelper.getInstance(application)
    var professorDao = roomInstance.professorDao()

     fun getProfessorLogin(id:Long,pass:String): Professor? {
        return professorDao.getProfessorLogin(id,pass)
    }
    fun getProfessorsLiveData():LiveData<List<Professor>>{
        return professorDao.getProfessorsLiveData()
    }


    fun getAllProfessors(): List<Professor> {
        return professorDao.getAllProfessors()
    }

     fun addProfessor(Professor: Professor):Long{
        return professorDao.addProfessor(Professor)
    }
    fun getProfessorUnfinishedWork():List<Professor>{
        return professorDao.getProfessorUnfinishedWork("")
    }

    suspend fun updateProfessor(Professor: Professor):Int {
        return professorDao.updateProfessor(Professor)
    }


    suspend fun getProfessor(id:Int): Professor?{
        return  professorDao.getProfessor(id)
    }
    fun removeProfessor(id:Int):Int{
        return professorDao.removeProfessor(id)
    }
    fun getOnlyFinishedProfessors():List<Professor>{
        return professorDao.getOnlyFinishedProfessors()
    }


}