package com.example.sample.message

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.databinding.ItemMessageRecievedBinding
import com.example.sample.databinding.ItemMessageSendBinding
import com.example.sample.databinding.ItemReplyMessageReceviedBinding
import com.example.sample.databinding.ItemReplyMessageSendBinding
import com.example.sample.message.ui.DeleteMessageListener
import com.example.sample.room.entity.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.coroutines.Dispatchers
import java.util.Collections.min
import kotlin.math.min


class MessageAdapter(
    var messages: List<AllMessage>,
    var user: User,
    var onDeleteMessageListener: DeleteMessageListener,
    var selectedPosition: Int = 0,
    var card: ConstraintLayout? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val SEND = 10
    private val RECEIVE = 20
    private val REPLY_SEND = 30
    private val REPLY_RECEIVE = 40


    class SendViewHolder(binding: ItemMessageSendBinding) : RecyclerView.ViewHolder(binding.root) {
        var message = binding.message
        var card = binding.card
        var time = binding.time
        var background = binding.cons

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ReceiveViewHolder(binding: ItemMessageRecievedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var message = binding.message
        var card = binding.card
        var time = binding.time
        var background = binding.cons

    }

    class ReplySendViewHolder(binding: ItemReplyMessageSendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var message = binding.message
        var card = binding.card
        var time = binding.time
        var name = binding.name
        var oldMsg = binding.oldMsg
        var background = binding.background

    }

    class ReplyReceivedViewHolder(binding: ItemReplyMessageReceviedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var message = binding.message
        var card = binding.card
        var time = binding.time
        var name = binding.name
        var oldMsg = binding.oldMsg
        var background = binding.background

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            REPLY_SEND -> {
                return ReplySendViewHolder(
                    ItemReplyMessageSendBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            REPLY_RECEIVE -> {
                return ReplyReceivedViewHolder(
                    ItemReplyMessageReceviedBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            SEND -> {
                return SendViewHolder(
                    ItemMessageSendBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> {
                return ReceiveViewHolder(
                    ItemMessageRecievedBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )

            }
        }
    }

    private fun convertMStoTime(millis: Long): String {
        var amOrPm = ""


        val date = Date(millis)
        val formatter = SimpleDateFormat("HH:mm")
        val res = formatter.format(date)
        val time = res.split(":")
        var hour = time[0].toInt()
        if (hour > 12) {
            hour -= 12
            amOrPm = "PM"
        } else {
            amOrPm = "AM"
        }

        return "$hour : ${time[1]} $amOrPm"


    }


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder) {
            is SendViewHolder -> {
                if (message.isSelected) {
                    Log.d(TAG, "onBindViewHolder: $position")
                    holder.background.setBackgroundColor(
                        holder.itemView.context.getColor(
                            R.color.message_background
                        )
                    )
                } else {
                    holder.background.setBackgroundColor(
                        holder.itemView.context.getColor(
                            R.color.white
                        )
                    )

                }
                holder.message.text = message.msg

//                holder.card.setOnTouchListener(
//                    View.OnTouchListener { v, event ->
//
//                        // variables to store current configuration of quote card.
//                        val displayMetrics = holder.itemView.context.resources.displayMetrics
//                        val cardWidth = holder.card.width
//                        val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - (cardWidth / 2)
//
//                        when (event.action) {
//                            MotionEvent.ACTION_UP -> {
//
//                                var currentX = holder.card.x
//                                holder
//                                    .card
//                                    .animate()
//                                    .x(cardStart)
//                                    .setDuration(150)
//                                    .setListener(
//                                        object : AnimatorListenerAdapter() {
//                                            override fun onAnimationEnd(animation: Animator) {
//
//                                                holder.card.animate().x(0f)
//                                            }
//                                        }
//                                    ).start()
//                            }
//                            MotionEvent.ACTION_MOVE -> {
//                                Log.d(TAG, "onBindViewHolder: action move")
//
//
//                                val newX = event.rawX
//                                if (newX - cardWidth < cardStart) {
//                                    holder
//                                        .card.animate()
//                                        .x(min(cardStart, newX - (cardWidth / 2)))
//                                        .setDuration(0)
//                                        .start()
//
//                                }
//                            }
//                        }
//                        v.performClick()
//                        return@OnTouchListener true
//                    })


                holder.card.setOnLongClickListener {
                    onDeleteMessageListener.onClick(position)
                    true
                }

                holder.time.text = convertMStoTime(message.ms)
            }
            is ReplySendViewHolder -> {
                if (message.isSelected) {
                    Log.d(TAG, "onBindViewHolder: $position")
                    holder.background
                        .setBackgroundColor(
                            holder.itemView.context.getColor(
                                R.color.message_background
                            )
                        )
                } else {
                    holder
                        .background
                        .setBackgroundColor(holder.itemView.context.getColor(R.color.white))

                }
                holder.message.text = message.msg
                holder.name.text = message.oldMsgName!!.toString()
                holder.oldMsg.text = message.oldMsg!!.toString()
                holder.time.text = convertMStoTime(message.ms)
                holder.card.setOnLongClickListener {
                    onDeleteMessageListener.onClick(position)
                    true
                }
            }
            is ReplyReceivedViewHolder -> {
                if (message.isSelected) {
                    Log.d(TAG, "onBindViewHolder: $position")
                    holder.background.setBackgroundColor(
                        holder.itemView.context.getColor(
                            R.color.message_background
                        )
                    )
                } else {
                    holder.background.setBackgroundColor(
                        holder.itemView.context.getColor(
                            R.color.white
                        )
                    )

                }
                holder.message.text = message.msg
                holder.name.text = message.senderName
                holder.oldMsg.text = message.oldMsg!!.toString()
                holder.time.text = convertMStoTime(message.ms)
                holder.card.setOnLongClickListener {
                    onDeleteMessageListener.onClick(position)
                    true
                }
            }
            else -> {

                val viewHolder = holder as ReceiveViewHolder
                if (message.isSelected) {
                    Log.d(TAG, "onBindViewHolder: $position")
                    holder.background.setBackgroundColor(
                        holder.itemView.context.getColor(
                            R.color.message_background
                        )
                    )
                } else {
                    holder.background.setBackgroundColor(
                        holder.itemView.context.getColor(
                            R.color.white
                        )
                    )

                }
                viewHolder.message.text = message.msg
                holder.time.text = convertMStoTime(message.ms)
                holder.card.setOnLongClickListener {
                    onDeleteMessageListener.onClick(position)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }


    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        selectedPosition = position
        if (message.oldMsg != null && user.identifier == message.senderId) {
            return REPLY_SEND
        } else if (message.oldMsg != null && user.identifier != message.senderId) {
            return REPLY_RECEIVE
        } else if (user.identifier == message.senderId) {
            return SEND
        }
        return RECEIVE

    }

    fun changeList(msgList: List<AllMessage>) {
        this.messages = msgList
        notifyDataSetChanged()
    }
}