package com.example.sample.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Admin(

    val name:String,
    val collegeID:Int,
    val adminID:Int,
    val password:String,
    var announcementsSize:Int = 0,
    var noOfReceivedMsg :Int = 0

):Actor(){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
