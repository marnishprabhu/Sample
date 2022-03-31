package com.example.sample.repository

import android.app.Application
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sample.room.database.RoomDatabaseHelper
import com.example.sample.room.entity.AllMessage
import com.example.sample.room.entity.MessageSize

class MessageRepository(val application: Application) {


    var roomInstance = RoomDatabaseHelper.getInstance(application)

    var allMsgDao = roomInstance.allMessageDao()
    var messageSizeDAO = roomInstance.messageSizeDao()

    fun addMsg(allMessage: AllMessage) :Long{
        return  allMsgDao.addMsg(allMessage)
    }

    fun getMsg(sendId: String, recId: String): ArrayList<AllMessage> {
        return allMsgDao.getMsg(sendId, recId) as ArrayList<AllMessage>
    }

    fun getAllMsgForCurrentUser(userId: String): List<AllMessage> {
        return allMsgDao.getAllMsgForCurrentUser(userId)
    }
    fun deleteForMe(id:String, myId:Int){
        allMsgDao.deleteForMe(id,myId)
    }




    fun addMessageSize(msgSize: MessageSize) {
        messageSizeDAO.addMessageSize(msgSize)
    }

    fun updateMessageCounter(msgSize: MessageSize) {
        messageSizeDAO.updateMessageCounter(msgSize)

    }

    fun getMessageSize(currentId: String, otherId: String):  List<MessageSize> {
        return messageSizeDAO.getMessageSize(currentId, otherId)
    }
    fun deleteForEveryOne(message: Int):Int{
       return allMsgDao.deleteForEveryOne(message)
    }
    fun getArgsMessage(currentId: String,otherId: String):MessageSize{
        return messageSizeDAO.getArgsMessage(currentId, otherId)
    }
    fun getTotalMsgSize(id:String):List<Int>{
        return messageSizeDAO.getTotalMsgSize(id)
    }
}