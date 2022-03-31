package com.example.sample.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Department(
    @PrimaryKey
    val deparmentID:Int,
    val departmentName:String,
    val hodID :Int
)
