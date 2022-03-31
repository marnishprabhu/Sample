package com.example.sample.bottomfragments.home.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.sample.bottomfragments.home.model.Event
import com.example.sample.databinding.ItemEventCardviewBinding
import com.example.sample.databinding.ItemEventHomeBinding

class EachEventViewholder(binding: ItemEventHomeBinding) : RecyclerView.ViewHolder(binding.root) {

    var title = binding.title
    var description = binding.description
    val date = binding.Date
    val time = binding.time

}
class EventViewholder(binding: ItemEventCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

    var recyclerView = binding.horizontalRecView
    var cardView = binding.eventCardview

}