package com.example.sample.leave

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sample.bottomfragments.home.model.HomeItems


@Entity
data class Leave(
    @PrimaryKey(autoGenerate = true)
    val key:Int,
    val reason:String,
    val fromDate:String,
    val toDate:String,
    val fromMilliSecond:Long,
    val toMilliSecond:Long,
    val name:String,
    var isAccepted:Boolean = false,
    var isRejected:Boolean = false,
    val studentId:String,
    val profId:Int
):HomeItems()
