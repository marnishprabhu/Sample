package com.example.sample.bottomfragments.profile.model

data class Item<T>(
    val key:String,
    val value:T
):ProfileItems()

 open class ProfileItems
