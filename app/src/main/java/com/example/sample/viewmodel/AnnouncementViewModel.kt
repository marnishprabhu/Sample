package com.example.sample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.leave.Leave
import com.example.sample.room.entity.Announcement
import com.example.sample.repository.AnnouncementRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AnnouncementViewModel(application: Application) : AndroidViewModel(application) {
    private val announcementRepository = AnnouncementRepository(application)

    suspend fun getAllAnnouncements(id: Int): List<Announcement> {
        return announcementRepository.getAllAnnouncements(id)
    }

    suspend fun addAnnouncement(announcement: Announcement) {
        viewModelScope.launch(IO) {
            announcementRepository.addAnnouncement(announcement)
        }.join()
    }

    fun getAllAnnouncementsFromProfessor(id: Int): List<Announcement>? {
        return announcementRepository.getAllAnnouncementsFromProfessor(id)
    }

    suspend fun getLoginID(): Int {
        return announcementRepository.getLoginID()
    }

    suspend fun getAnnouncement(id: Int): Announcement {
        return announcementRepository.getAnnouncement(id)
    }

    suspend fun updateAnnouncement(title: String, desc: String, visiblity: Int, primaryID: Int) {
        announcementRepository.updateAnnouncement(title, desc, visiblity, primaryID)
    }

    suspend fun removeAnnouncement(id: Int) {
        announcementRepository.removeAnnouncement(id)
    }


    fun getAdminUserAnnouncements():List<Announcement>{
        return  announcementRepository.getAdminUserAnnouncements()
    }

    fun getProfessorUserAnnouncements():List<Announcement>{
        return announcementRepository.getProfessorUserAnnouncements()
    }

    fun getStudentUserAnnouncements():List<Announcement>{
        return  announcementRepository.getStudentUserAnnouncements()
    }

    fun addLeaveRequest(leave: Leave):Long{
       return announcementRepository.addLeaveRequest(leave)
    }

    fun getLeavesForProfId(id:Int):List<Leave>{
        return announcementRepository.getLeavesForProfId(id)
    }

    fun getStatusOfLeave(id:String):List<Leave>{
        return  announcementRepository.getStatusOfLeave(id)
    }
    fun deleteAll(){
        return announcementRepository.deleteAll()
    }
    fun updateLeave(leave: Leave):Int{
        return announcementRepository.updateLeave(leave)
    }

}