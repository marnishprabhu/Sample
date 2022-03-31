package com.example.sample.room.dao

import androidx.room.*
import com.example.sample.room.entity.Announcement


@Dao
interface AnnouncementDAO {

    @Query("SELECT * FROM ANNOUNCEMENT WHERE  visibilityID = 70 or visibilityID = 40 or visibilityID = 50 order by id desc")
    fun getAdminUserAnnouncements():List<Announcement>


    @Query("SELECT * FROM ANNOUNCEMENT WHERE  visibilityID = 40 or visibilityID = 60 or visibilityID = 70  order by id desc")
    fun getProfessorUserAnnouncements():List<Announcement>



    @Query("SELECT * FROM ANNOUNCEMENT WHERE visibilityID = 50 or visibilityID = 60 or visibilityID = 70 order by id desc")
    fun getStudentUserAnnouncements():List<Announcement>

    @Query("SELECT * FROM announcement WHERE visibilityID = :id ORDER BY id DESC")
    fun getAllAnnouncements(id: Int): List<Announcement>

    @Query("SELECT * FROM announcement WHERE uniqueId = :id")
    fun getAllAnnouncementsFromProfessor(id: Int): List<Announcement>?
    @Insert
    fun addAnnouncement(announcement: Announcement)


    @Query("DELETE FROM ANNOUNCEMENT WHERE id = :id")
    fun removeAnnouncement(id:Int)


    @Query("SELECT * FROM ANNOUNCEMENT WHERE id = :id")
    fun getAnnouncement(id: Int): Announcement

    @Query("UPDATE ANNOUNCEMENT SET title = :title ,description = :desc ,visibilityID = :visiblity where id = :primaryID")
    fun updateAnnouncement(title: String,desc:String,visiblity:Int,primaryID:Int)
}