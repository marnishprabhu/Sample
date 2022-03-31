package com.example.sample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sample.repository.MessageRepository
import com.example.sample.room.entity.AllMessage
import com.example.sample.room.entity.MessageSize

class AllMessageViewModel(application: Application) : AndroidViewModel(application) {
    private val messageRepository = MessageRepository(application)


    fun addMsg(allMessage: AllMessage):Long {
       return messageRepository.addMsg(allMessage)
    }

    fun getMsg(sendId: String, recId: String):ArrayList<AllMessage> {
        return messageRepository.getMsg(sendId, recId)
    }
    fun getAllMsgForCurrentUser(userId:String):List<AllMessage>{
        return messageRepository.getAllMsgForCurrentUser(userId)
    }
    fun deleteForMe(id:String, myId:Int){
        messageRepository.deleteForMe(id,myId)
    }
    fun addMessageSize(msgSize: MessageSize) {
        messageRepository.addMessageSize(msgSize)
    }

    fun updateMessageCounter(msgSize: MessageSize) {
        messageRepository.updateMessageCounter(msgSize)

    }

    fun getMessageSize(currentId: String, otherId: String): List<MessageSize> {
        return messageRepository.getMessageSize(currentId, otherId)
    }
    fun deleteForEveryOne(message: Int):Int{
       return messageRepository.deleteForEveryOne(message)
    }
    fun getArgsMessage(currentId: String,otherId: String):MessageSize?{
        return  messageRepository.getArgsMessage(currentId, otherId)
    }
    fun getTotalMsgSize(id:String):List<Int>{
        return messageRepository.getTotalMsgSize(id)
    }

}