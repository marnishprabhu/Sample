package com.example.sample.addprofessor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sample.room.entity.Professor
import com.example.sample.repository.ProfessorRepository
import com.example.sample.room.entity.User
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async

class ProfessorViewModel(application: Application) : AndroidViewModel(application) {
    var professor: Professor = Professor(null)
    var repository = ProfessorRepository(application)
    var user: User? = null

    fun getAllProfessors(): List<Professor> {
        return repository.getAllProfessors()
    }

    fun getProfessorsLiveData(): LiveData<List<Professor>> {
        return repository.getProfessorsLiveData()
    }

    fun getProfessorUnfinishedWork(): List<Professor> {
        return repository.getProfessorUnfinishedWork()
    }

    suspend fun addProfessor(professor: Professor): Long {
        this.professor = professor
        return viewModelScope.async(IO) {
            repository.addProfessor(professor)
        }.await()
    }

    suspend fun updateProfessor(professor: Professor): Int {
        this.professor = professor
        return viewModelScope.async(IO) {
            repository.updateProfessor(professor)
        }.await()

    }

    suspend fun getProfessor(id: Int): Professor? {
        return repository.getProfessor(id)
    }

    fun removeProfessor(id: Int): Int {
        return repository.removeProfessor(id)
    }
    fun getOnlyFinishedProfessors():List<Professor>{
        return repository.getOnlyFinishedProfessors()
    }

}