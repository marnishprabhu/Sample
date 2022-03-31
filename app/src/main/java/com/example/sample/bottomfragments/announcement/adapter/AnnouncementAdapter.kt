package com.example.sample.bottomfragments.announcement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.bottomfragments.announcement.OnItemClick
import com.example.sample.bottomfragments.announcement.viewholder.AnnouncementViewholder
import com.example.sample.bottomfragments.announcement.viewholder.LeaveViewHolder
import com.example.sample.bottomfragments.home.model.HomeItems
import com.example.sample.databinding.ItemAnnouncementBinding
import com.example.sample.databinding.ItemLeaveApplyBinding
import com.example.sample.leave.Leave
import com.example.sample.room.entity.Announcement
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import java.text.DateFormat

class AnnouncementAdapter(
    var annoucements: List<HomeItems>,
    var onItemClick: OnItemClick? = null,
    var user: User,
    private val onLeaveItemClickListener: OnLeaveItemClickListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ANNOUNCEMENTS = 10
    private val LEAVE = 20
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            ANNOUNCEMENTS -> {
                AnnouncementViewholder(
                    ItemAnnouncementBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }

            else -> {
                LeaveViewHolder(
                    ItemLeaveApplyBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AnnouncementViewholder -> {
                val anno = annoucements[position] as Announcement
                holder.title.text = anno.title
                holder.desc.text = anno.description
                // CHECK LOGIN
                val currentLogin = user.loginID
                if (currentLogin == Constants.STUDENT) {
                    holder.image.visibility = GONE
                } else if (currentLogin == Constants.PROFESSOR) {
                    if (anno.uniqueId == null) {
                        holder.image.visibility = GONE

                    } else {
                        holder.image.visibility = VISIBLE
                        holder.image.setImageResource(R.drawable.ic_item)
                        holder.image.setOnClickListener {
                            createItemMenu(holder.image, anno)
                        }
                    }
                } else {
                    holder.image.setImageResource(R.drawable.ic_item)
                    holder.image.setOnClickListener {
                        createItemMenu(holder.image, anno)
                    }
                }
            }
            is LeaveViewHolder -> {
                val leave = annoucements[position] as Leave

                if (user.loginID == Constants.PROFESSOR) {
                    holder.statusText.visibility = VISIBLE
                    holder.status.visibility = VISIBLE
                    holder.request.visibility = VISIBLE

                    holder.request.text = "Roll Number:   "
                    holder.actualName.text = leave.name
                    holder.studentId.text = leave.studentId

                    holder.studentId.visibility = VISIBLE
                    holder.accept.visibility = VISIBLE
                    holder.decline.visibility = VISIBLE
                    if (!leave.isRejected && leave.isAccepted) {
                        holder.status.text = "Accepted"
                        holder.status.setTextColor(holder.itemView.resources.getColor(R.color.button1))
                        holder.accept.visibility = GONE
                        holder.decline.visibility = GONE


                    } else if (leave.isRejected && !leave.isAccepted) {
                        holder.status.text = "Declined"
                        holder.status.setTextColor(holder.itemView.resources.getColor(R.color.red))

                        holder.accept.visibility = GONE
                        holder.decline.visibility = GONE
                    }


                } else if (user.loginID == Constants.STUDENT) {
                    holder.studentId.visibility = INVISIBLE
                    holder.accept.visibility = GONE
                    holder.decline.visibility = GONE
                    holder.studentId.visibility = GONE
                    holder.request.visibility = GONE
                    holder.nameText.visibility = GONE
                    holder.actualName.visibility  =GONE


                    if (!leave.isRejected && leave.isAccepted) {
                        holder.status.text = "Accepted"
                        holder.status.setTextColor(holder.itemView.resources.getColor(R.color.button1))

                    } else if (leave.isRejected && !leave.isAccepted) {
                        holder.status.text = "Declined"
                        holder.status.setTextColor(holder.itemView.resources.getColor(R.color.red))

                    }


                }
                holder.content.text = leave.reason
//                val dates = "${leave.fromDate} - ${leave.toDate} "
                val fromMS = leave.fromMilliSecond
                val toMS = leave.toMilliSecond
                val fromDate = DateFormat.getDateInstance().format(fromMS)
                val toDate = DateFormat.getDateInstance().format(toMS)
                val dates = "$fromDate - $toDate"

                holder.dates.text = dates
                holder.accept.setOnClickListener {
                    onLeaveItemClickListener?.onClick(Constants.LEAVE_ACCEPT, position)

                }
                holder.decline.setOnClickListener {
                    onLeaveItemClickListener?.onClick(Constants.LEAVE_DECLINE, position)

                }

            }
        }

    }

    private fun createItemMenu(image: ImageView, anno: Announcement) {
        val popup = PopupMenu(context, image)
        popup.menuInflater.inflate(R.menu.announcment_item_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuitem ->
            when (menuitem.itemId) {
                R.id.remove -> {
                    onItemClick?.onRemove(anno.id)
                }
                R.id.edit -> {
                    onItemClick?.edit(anno.id.toString())
                }
            }
            true
        }
        popup.show()
    }

    override fun getItemCount(): Int {
        return annoucements.size
    }

    fun changeList(list: List<HomeItems>) {
        this.annoucements = list
        notifyDataSetChanged()
    }

    fun changeApp(user: User) {
        this.user = user
    }

    override fun getItemViewType(position: Int): Int {
        return when (annoucements[position]) {
            is Announcement -> ANNOUNCEMENTS
            else -> {
                LEAVE
            }
        }
    }
}

interface OnLeaveItemClickListener {
    fun onClick(identifier: Int, pos: Int)
}