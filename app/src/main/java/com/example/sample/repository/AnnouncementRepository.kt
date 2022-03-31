package com.example.sample.repository

import android.app.Application
import androidx.room.Query
import com.example.sample.leave.Leave
import com.example.sample.room.entity.Announcement
import com.example.sample.room.database.RoomDatabaseHelper

class AnnouncementRepository(application: Application) {

    private var roomInstance = RoomDatabaseHelper.getInstance(application)
    private val announcementDAO = roomInstance.announcementDao()
    private val appDAO = roomInstance.appDao()
    private val leaveDao = roomInstance.leaveDao()

    suspend fun getAllAnnouncements(id: Int): List<Announcement> {
        return announcementDAO.getAllAnnouncements(id)
    }
    fun getAllAnnouncementsFromProfessor(id: Int): List<Announcement>?{
        return announcementDAO.getAllAnnouncementsFromProfessor(id)
    }

    suspend fun addAnnouncement(announcement: Announcement) {
        announcementDAO.addAnnouncement(announcement)
    }

    suspend fun getLoginID(): Int {
        return appDAO.getLoginID()
    }

    fun getAnnouncement(id: Int): Announcement {
        return announcementDAO.getAnnouncement(id)
    }

    fun updateAnnouncement(title: String, desc: String, visiblity: Int, primaryID: Int) {
        announcementDAO.updateAnnouncement(title, desc, visiblity, primaryID)
    }

    fun removeAnnouncement(id: Int) {
        announcementDAO.removeAnnouncement(id)
    }



    fun getAdminUserAnnouncements():List<Announcement>{
        return  announcementDAO.getAdminUserAnnouncements()
    }

    fun getProfessorUserAnnouncements():List<Announcement>{
        return announcementDAO.getProfessorUserAnnouncements()
    }

    fun getStudentUserAnnouncements():List<Announcement>{
        return  announcementDAO.getStudentUserAnnouncements()
    }




    fun addLeaveRequest(leave: Leave):Long{
        return  leaveDao.addLeaveRequest(leave)
    }

    fun getLeavesForProfId(id:Int):List<Leave>{
        return leaveDao.getLeavesForProfId(id)
    }

    fun getStatusOfLeave(id:String):List<Leave>{
        return  leaveDao.getStatusOfLeave(id)
    }
    fun deleteAll(){
        return leaveDao.deleteAll()
    }
    fun updateLeave(leave: Leave):Int{
        return leaveDao.updateLeave(leave)
    }

}