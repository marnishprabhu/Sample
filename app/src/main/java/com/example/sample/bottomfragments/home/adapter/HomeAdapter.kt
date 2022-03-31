package com.example.sample.bottomfragments.home.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.bottomfragments.home.HomeFragment
import com.example.sample.bottomfragments.home.model.*
import com.example.sample.bottomfragments.home.viewholders.EventViewholder
import com.example.sample.bottomfragments.home.viewholders.PeopleViewholder
import com.example.sample.bottomfragments.home.viewholders.TitleViewholder
import com.example.sample.databinding.*
import com.example.sample.home.HomeItemClickListener
import com.example.sample.utils.Home
import java.lang.Exception
import kotlin.collections.ArrayList

class HomeAdapter(
    var list: ArrayList<HomeItems>,
    var listener: HomeItemClickListener,
    var isProfViewAll: Boolean = false,
    var isStudentViewAll: Boolean = false,
    var isEventsViewAll: Boolean = false
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val EVENTS_ID = 1
    private val PEOPLE_ID = 2
    private val TITLE_ID = 3

    private var dataList = list
    lateinit var nestedAdapter: NestedAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EVENTS_ID -> {
                EventViewholder(
                    ItemEventCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            PEOPLE_ID -> {
                PeopleViewholder(
                    ItemPeopleCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            TITLE_ID -> {
                TitleViewholder(
                    ItemTitleHomeBinding.inflate(
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
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventViewholder -> {
                val recyclerView = holder.recyclerView
                val events = dataList[position] as EventsList
                nestedAdapter = NestedAdapter(events.listEvents, listener)
                if (events.listEvents.isEmpty()) {
                    holder.cardView.visibility = VISIBLE
                } else {

                    holder.cardView.visibility = GONE
                }
                recyclerView.adapter = nestedAdapter
            }
            is TitleViewholder -> {
                val title = dataList[position]
                if (title is Title) {
                    holder.title.text = title.title
                    holder.title.setTextColor(holder.title.resources.getColor(R.color.black))
                    holder.divider.visibility = GONE
                    holder.image.visibility = GONE
                    holder.number.visibility = GONE
                    if (title.title == "Upcoming Events") {

                        if (isEventsViewAll) {
                            Log.d(TAG, "onBindViewHolder: upcoming 1")

                            holder.viewAll.visibility = VISIBLE
                        } else {
                            Log.d(TAG, "onBindViewHolder: upcoming 2")

                            holder.viewAll.visibility = GONE
                        }
                    }

                    if (title.title == "Students") {
                        if (isStudentViewAll) {
                            Log.d(TAG, "onBindViewHolder: 23 45")
                            holder.viewAll.visibility = VISIBLE
                        } else {
                            Log.d(TAG, "onBindViewHolder: 23 49")

                            holder.viewAll.visibility = GONE
                        }
                    }
                    if (title.title == "Professors") {
                        if (isProfViewAll) {
                            holder.viewAll.visibility = VISIBLE
                        } else {
                            holder.viewAll.visibility = GONE
                        }
                    }

                    if (title.title == "Admin") {
                        holder.viewAll.visibility = GONE
                    }

                    holder.title.visibility = VISIBLE
                    holder.top.visibility = VISIBLE
                } else if (title is UnfinishedTitle) {
                    holder.title.text = title.title
                    if (title.numberOfItems == 0 || title.numberOfItems == null) {
                        holder.divider.visibility = GONE
                        holder.image.visibility = GONE
                        holder.number.visibility = GONE
                        holder.title.visibility = GONE
                        holder.viewAll.visibility = GONE
                        holder.top.visibility = GONE
                    } else {

                        holder.title.setTextColor(holder.title.resources.getColor(R.color.home_red))
                        holder.number.setTextColor(holder.title.resources.getColor(R.color.home_red))
                        holder.divider.visibility = VISIBLE
                        holder.image.visibility = VISIBLE
                        holder.number.visibility = VISIBLE
                        holder.title.visibility = VISIBLE
                        holder.viewAll.visibility = VISIBLE
                        holder.top.visibility = VISIBLE
                        holder.number.text = title.numberOfItems.toString()
                    }
                }

                holder.viewAll.setOnClickListener {
                    listener.onViewAll(position)
                }

            }

            is PeopleViewholder -> {
                val recyclerView = holder.recyclerView
                val peoples = dataList[position] as PeoplesList
                nestedAdapter = NestedAdapter(peoples.listPeoples, listener)
                if (peoples.listPeoples.isEmpty()) {
                    holder.cardView.visibility = VISIBLE
                } else {
                    holder.cardView.visibility = GONE

                }
                recyclerView.adapter = nestedAdapter

            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is
            Title -> TITLE_ID
            is
            UnfinishedTitle -> TITLE_ID
            is
            EventsList -> EVENTS_ID
            is
            PeoplesList -> PEOPLE_ID
            else
            -> throw Exception()
        }
    }

    fun changeList(list: ArrayList<HomeItems>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    fun changeIsProfViewAll(isProfViewAll: Boolean) {
        this.isProfViewAll = isProfViewAll
        notifyDataSetChanged()

    }

    fun changeIsStudentViewAll(isStudentViewAll: Boolean) {
        this.isStudentViewAll = isStudentViewAll
        notifyDataSetChanged()

    }

    fun changeIsEventsViewAll(isEventsViewAll: Boolean) {
        this.isEventsViewAll = isEventsViewAll
        notifyDataSetChanged()

    }
}