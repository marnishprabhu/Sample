package com.example.sample.bottomfragments.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.R

import com.example.sample.bottomfragments.home.model.People
import com.example.sample.bottomfragments.home.viewholders.EachEventViewholder
import com.example.sample.bottomfragments.home.viewholders.EachPeopleViewholder
import com.example.sample.databinding.ItemEventHomeBinding
import com.example.sample.databinding.ItemPeopleHomeBinding
import com.example.sample.home.HomeItemClickListener
import com.example.sample.room.entity.Event
import java.lang.Exception

class NestedAdapter(var dataList: List<Any>,
var onHomeItemClickListener: HomeItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val PEOPLES_ID = 10
    private val EVENTS_ID = 20
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        return when (viewType) {
            PEOPLES_ID -> {

                EachPeopleViewholder(
                    ItemPeopleHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            EVENTS_ID -> {
                EachEventViewholder(
                    ItemEventHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is People -> PEOPLES_ID
            is Event -> EVENTS_ID
            else -> {
                throw Exception()
            }
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EachEventViewholder -> {
                val events: List<Event> = dataList as List<Event>
                val event = events[position]
                holder.title.text = event.eventTitle
                holder.description.text = event.eventDescription
                holder.date.text = event.date
                holder.time.text = event.time

            }
            is EachPeopleViewholder -> {
                val list: List<People> = dataList as List<People>
                val people = list[position]

                Glide
                    .with(holder.itemView.context)
                    .load(people.image)
                    .placeholder(R.drawable.ic_profile)
                    .into(holder.profile)
                holder.id.text = people.id.toString()
                holder.name.text = people.name
                holder.cardview.setOnClickListener {
                    onHomeItemClickListener.onClick(list,position,people.type)
                }
            }
        }
    }
    fun changeList(list: List<Any>) {
        this.dataList = list
        notifyDataSetChanged()
    }
}