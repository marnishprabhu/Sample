package com.example.sample.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.sample.room.entity.Admin


@Dao
interface AdminDAO {

    @Query("DELETE FROM ADMIN WHERE adminID = :id")
    fun removeAdmin(id:Int)

    @Query("UPDATE ADMIN SET password = :pass")
    fun updatePassword(pass:String):Int


    @Query("SELECT * FROM ADMIN")
    fun getAllAdmins():List<Admin>

    @Query("SELECT * FROM ADMIN")
    fun getAllAdminsLiveData():LiveData<List<Admin>>

    @Insert(onConflict = REPLACE)
    fun addAdmin(admin:Admin)

    @Query("SELECT * FROM ADMIN where adminID = :id and password =:password")
    fun getLoginID(id:Int,password:String): Admin?

    @Query("SELECT adminID FROM ADMIN order by adminID desc limit 1")
    fun getAdminID():Int

    @Query("SELECT * FROM ADMIN where adminID = :id ")
    fun getAdmin(id:Int):Admin?

    @Update
    fun updateAdmin(admin: Admin)

    @Query("SELECT * FROM ADMIN WHERE collegeID = :id and adminID = :adminID")
    fun getAdmins(id:Int,adminID:Int):Admin?
}