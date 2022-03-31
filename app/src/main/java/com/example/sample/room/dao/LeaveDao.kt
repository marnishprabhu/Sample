package com.example.sample.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sample.leave.Leave


@Dao
interface LeaveDao {


    @Insert
    fun addLeaveRequest(leave: Leave): Long

    @Query("SELECT * FROM LEAVE WHERE profId = :id ORDER BY fromMilliSecond asc ")
    fun getLeavesForProfId(id: Int): List<Leave>

    @Query("SELECT * FROM LEAVE WHERE studentId = :id ORDER BY fromMilliSecond asc")
    fun getStatusOfLeave(id: String): List<Leave>

    @Query("DELETE  FROM LEAVE")
    fun deleteAll()

    @Update
    fun updateLeave(leave: Leave):Int
}