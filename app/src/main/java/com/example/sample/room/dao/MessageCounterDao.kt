package com.example.sample.room.dao

import androidx.room.*
//import com.example.sample.room.entity.MessageCounter

//
//@Dao
//interface MessageCounterDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addMessageCounter(messageCounter: MessageCounter)
//
//    @Update
//    fun updateMessageCounter(messageCounter: MessageCounter)
//
//    @Query("SELECT noOfMessagesStudent from MessageCounter where profID = :id or studentId = :id ")
//    fun getMessageSize(id: String): List<Int>
//
//
//    @Query("SELECT profID from MessageCounter where studentId =:stuId ")
//    fun getProfessorIds(stuId: String): List<String>
//
//
//    @Query("SELECT studentId from MessageCounter where profID =:profId")
//    fun getStudentIds(profId: String): List<String>
//
//    @Query("SELECT noOfMessagesStudent from MessageCounter where profID = :profId and  studentId = :stuId ")
//    fun getStudentReceivedMessageSizeForParticularField(profId: String, stuId: String): Int
//    @Query("SELECT noOfMessagesProfessor from MessageCounter where profID = :profId and  studentId = :stuId ")
//    fun getProfessorReceivedMessageSizeForParticularField(profId: String, stuId: String): Int
//
//}