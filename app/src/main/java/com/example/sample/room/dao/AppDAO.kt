package com.example.sample.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sample.room.entity.User


@Dao
interface AppDAO {


    @Query("SELECT loginID FROM User")
    fun getLoginID(): Int

    @Query("SELECT * FROM User")
    fun getUser():User?

    @Query("DELETE FROM User")
    fun clear()

    @Insert
    fun changeUser(user: User)
}