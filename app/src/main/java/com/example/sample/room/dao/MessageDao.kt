package com.example.sample.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
//import com.example.sample.room.entity.Message

//
//@Dao
//interface MessageDao {
//
//    @Query("SELECT * FROM Message WHERE profID = :profId and studentId = :studentId order by milliSec asc")
//    fun getMessages(profId:String,studentId:String):List<Message>
//
//    @Insert
//    fun addMessage(message: Message)
//
//    @Query("SELECT * FROM Message where profID =:profId order by milliSec desc ")
//    fun getMessagesFromProfessorId(profId: String):List<Message>
//
//    @Query("SELECT * FROM Message where studentId =:studentId order by milliSec desc")
//    fun getMessagesFromStudentId(studentId: String):List<Message>
//}