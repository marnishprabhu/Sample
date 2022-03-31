package com.example.sample.room.entity

import androidx.room.*
import retrofit2.http.DELETE

@Entity
data class MessageSize (
    var personOneId:String,
    var personTwoId:String,
    var noOfMessages:Int
        ){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}



@Dao
interface MessageSizeDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessageSize(msgSize: MessageSize)

    @Update
    fun updateMessageCounter(msgSize: MessageSize)

    @Query("SELECT * from MessageSize where personOneId = :currentId and personTwoId = :otherId  or personOneId = :otherId and personTwoId = :currentId ")
    fun getMessageSize(currentId: String,otherId:String): List<MessageSize>

    @Query("SELECT * from MessageSize where personOneId = :currentId and personTwoId = :otherId  ")
    fun getArgsMessage(currentId: String,otherId: String):MessageSize



    @Query("SELECT  noOfMessages from MessageSize where personTwoId = :id or personOneId =:id")
    fun getTotalMsgSize(id:String):List<Int>

}
