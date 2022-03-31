package com.example.sample.bottomfragments.announcement.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemAnnouncementBinding
import com.example.sample.databinding.ItemLeaveApplyBinding

class AnnouncementViewholder(binding: ItemAnnouncementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var title = binding.title
    var desc = binding.description
    var image = binding.options

}

class LeaveViewHolder(binding: ItemLeaveApplyBinding) : RecyclerView.ViewHolder(binding.root) {


    var content = binding.content
    var dates = binding.dates
    var status = binding.status
    var studentId = binding.clgNum
    var statusText = binding.statusText
    var request = binding.request
    var nameText  =binding.nametext
    var actualName = binding.actualName

    var accept = binding.accept
    var decline = binding.decline
}