package com.example.sample.message

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemAllMessagesBinding
import com.example.sample.home.OnMessageClickListener
import com.example.sample.room.entity.AllMessage
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants

class AllMessageAdapter(
    var userMessages: List<AllMessage>,
    var user: User,
    var onMessageClickListener: OnMessageClickListener,
    var sizeList: ArrayList<Int>
) : RecyclerView.Adapter<AllMessageAdapter.AllMessageViewHolder>() {


    class AllMessageViewHolder(binding: ItemAllMessagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var proPic = binding.proPic
        var message = binding.message
        var name = binding.name
        var card = binding.cardview
        var msgSize = binding.numberOfMsg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMessageViewHolder {
        return AllMessageViewHolder(
            ItemAllMessagesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AllMessageViewHolder, position: Int) {
        val message = userMessages[position]
        Log.d(TAG, "onBindViewHolder: ${message.senderName}")
        Log.d(TAG, "onBindViewHolder: ${message.recName}")


        if (user.identifier == message.recId) {
            holder.name.text = message.senderName.toString()
        } else {
            holder.name.text = message.recName.toString()
        }
        holder.message.text = message.msg


        holder.card.setOnClickListener {
            onMessageClickListener.onClick(userMessages[position])
        }
        val size = sizeList[position]
        if (size > 0) {
            Log.d(TAG, "onBindViewHolder:12 ")

            holder.msgSize.visibility = VISIBLE
            holder.msgSize.text = sizeList[position].toString()
        } else {

            holder.msgSize.visibility = GONE

        }
    }

    override fun getItemCount(): Int {
        return userMessages.size
    }

    fun changeList(list: ArrayList<AllMessage>, sizeList: ArrayList<Int>) {

        this.userMessages = list
        this.sizeList = sizeList
        notifyDataSetChanged()

    }
}