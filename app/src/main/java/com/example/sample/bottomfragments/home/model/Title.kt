package com.example.sample.bottomfragments.home.model

data class Title(
    val title:String):
    HomeItems()

data class UnfinishedTitle(
    val title: String,
    val numberOfItems:Int?
):HomeItems()
