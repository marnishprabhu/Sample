package com.example.sample.room.dao

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sample.room.entity.Event


@Dao
interface EventDAO {

    @Query("UPDATE EVENT SET eventTitle = :title , eventDescription = :description , date = :date, time = :time WHERE eventID =:id")
    fun updateEvent(id:Int, title:String,
                    description:String,date:String,time:String)


    @Insert
    fun addEvent(event: Event)

    @Query("DELETE  FROM EVENT WHERE :currentMilli > millisecond ")
    fun deleteFinishedEvents(currentMilli:Long):Int

    @Query("SELECT * FROM EVENT order by millisecond asc")
    fun getEvents():LiveData<List<Event>>


    @Query("SELECT * FROM EVENT WHERE eventID =  :eventID")
    fun getEvent(eventID:Int):Event


    @Query("DELETE  FROM EVENT WHERE eventID = :id")
    fun deleteEvent(id:Int)
}