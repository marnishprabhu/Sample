package com.example.sample.room.entity

import androidx.room.*

@Entity(tableName = "message_table")
data class AllMessage(
    var senderId: String,
    var recId: String,
    var msg: String,

    var ms:Long,
    var senderName:String,
    var recName:String,
    var senderType:Int,
    var recType:Int,
    var deleteFor:String? = null,
    var oldMsg:String? = null,
    var oldMsgName:String? = null



    ):Actor(){
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @Ignore
    var isSelected : Boolean = false
}

@Dao
interface AllMessageDao{


   @Insert
    fun addMsg(allMessage: AllMessage):Long
    @Query("SELECT * FROM message_table where  senderId = :senderId and recId =:recId or senderId=:recId and recId = :senderId order by ms asc")
    fun getMsg(senderId: String,recId: String):List<AllMessage>



    @Query("SELECT * FROM message_table where senderId = :userId or recId =:userId order by ms desc")
    fun getAllMsgForCurrentUser(userId:String):List<AllMessage>


    @Query("update message_table set deleteFor = :id where id = :myId ")
    fun deleteForMe(id:String, myId:Int)

    @Query("DELETE FROM message_table where id = :allMessage")
    fun deleteForEveryOne(allMessage: Int):Int

}
