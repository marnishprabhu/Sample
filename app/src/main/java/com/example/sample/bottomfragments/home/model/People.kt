package com.example.sample.bottomfragments.home.model

import com.example.sample.room.entity.Actor

data class People(
    val id:String,
    val name:String,
    val image:String,
    var type:Int
):Actor()


