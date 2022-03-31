package com.example.sample.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sample.bottomfragments.home.model.HomeItems


@Entity
data class Event(
    val eventTitle:String,
    val eventDescription:String,
    val date:String,
    val time:String,
    var millisecond:Long

): Actor() {
    @PrimaryKey(autoGenerate = true)
    var eventID = 0
}
