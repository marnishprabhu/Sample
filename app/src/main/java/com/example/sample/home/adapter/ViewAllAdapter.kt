package com.example.sample.home.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.R
import com.example.sample.bottomfragments.announcement.OnItemClick
import com.example.sample.databinding.ItemEventViewAllBinding
import com.example.sample.databinding.ItemViewAllBinding
import com.example.sample.home.OnMessageClickListener
import com.example.sample.room.entity.*
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ViewAllAdapter(
    var dataList: List<Any>,
    var onItemClick: OnItemClick? = null,
    var name: String? = null,
    var user: User? = null,
    var onmessageClickListener: OnMessageClickListener,
    var changeFilter: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    val EVENTS = 10
    val OTHERS = 20
    val scope = CoroutineScope(IO)
    var duplicate: ArrayList<Any> = ArrayList<Any>(dataList)
    var alteredLists = ArrayList<Any>()
    val departmentMap = HashMap<Int, String>()
    val loginID = user?.loginID
    private fun setDepartmentMap() {

        departmentMap.put(10, "Computer science and Engineering")
        departmentMap.put(20, "Electronics and communication Engineering")
        departmentMap.put(30, "Electronics and electrical Engineering")
        departmentMap.put(40, "Mechanical Engineering")
        departmentMap.put(50, "Civil Engineering")
    }

    init {
        Log.d(TAG, "context: ${duplicate.size} ")
        setDepartmentMap()
    }

    class ItemViewAllViewHolder(val binding: ItemViewAllBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var profile = binding.profilePic
        var name = binding.name
        var id = binding.clgId
        var options = binding.options
        var deptId = binding.departmentId
        val card = binding.cardview
        val msg = binding.message

    }

    class EventViewAllViewHolders(binding: ItemEventViewAllBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var title = binding.title
        var description = binding.description
        val date = binding.Date
        val time = binding.time
        val options = binding.options

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            OTHERS -> {
                ItemViewAllViewHolder(
                    ItemViewAllBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> {
                EventViewAllViewHolders(
                    ItemEventViewAllBinding.inflate(
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
            is ItemViewAllViewHolder -> {
                val con = holder.itemView.context
                Glide.with(con)
                    .load("https://picsum.photos/id/1005/5760/3840")
                    .placeholder(R.drawable.ic_profile)
                    .into(holder.profile)

                when (dataList[position]) {
                    is Admin -> {
                        val admin = dataList[position] as Admin
                        holder.id.text = admin.collegeID.toString()
                        holder.name.text = admin.name
                        holder.deptId.text = admin.adminID.toString()
                        holder.options.visibility = GONE

                    }
                    is Professor -> {

                        val professor = dataList[position] as Professor
                        holder.id.text = professor.professorID.toString()
                        val fullName = "${professor.firstName} ${professor.lastName}"
                        holder.name.text = fullName
                        val departmentName = departmentMap.get(professor.departmentID)
                        holder.deptId.text = departmentName!!
                        holder.msg.setOnClickListener {
                            onmessageClickListener.onClick(professor)
                        }
                        holder.card.setOnClickListener {
                            onItemClick?.onclick(position)
                        }
                        if (user?.identifier != professor.professorID.toString()) {
                            holder.msg.visibility = VISIBLE

                        } else {
                            holder.msg.visibility = GONE

                        }
                        if (loginID == null || loginID == Constants.ADMIN) {
                            holder.options.visibility = VISIBLE
                            holder.options.setOnClickListener {

                                createItemMenu(holder.options, professor, con)
                            }

                        } else {
                            holder.options.visibility = GONE
                        }

                    }
                    is Student -> {
                        val student = dataList[position] as Student
                        holder.id.text = student.clg_num.toString()
                        val fullName = "${student.firstName} ${student.lastName}"
                        holder.name.text = fullName
                        holder.deptId.text = student.other?.departmentName
                        holder.msg.setOnClickListener {
                            onmessageClickListener.onClick(student)
                        }
                        holder.card.setOnClickListener {
                            onItemClick?.onclick(position)
                        }
                        if (user?.identifier != student.clg_num.toString()) {
                            holder.msg.visibility = VISIBLE

                        } else {
                            holder.msg.visibility = GONE

                        }
                        when (loginID) {
                            Constants.ADMIN -> {
                                holder.options.visibility = VISIBLE
                                holder.options.setOnClickListener {

                                    createItemMenu(holder.options, student, con)
                                }


                            }
                            Constants.STUDENT -> {

                                holder.options.visibility = GONE

                            }
                            Constants.PROFESSOR -> {
                                holder.options.visibility = GONE

                                holder.options.setOnClickListener {

                                    createItemMenu(holder.options, student, con)
                                }

                            }

                        }

                    }
                }


            }
            is EventViewAllViewHolders -> {
                var events: List<Event> = dataList as List<Event>
                var event = events[position]
                holder.title.text = event.eventTitle
                holder.description.text = event.eventDescription
                holder.date.text = event.date
                holder.time.text = event.time

                if (loginID == Constants.ADMIN) {
                    holder.options.setOnClickListener {
                        val con = holder.itemView.context
                        createItemMenu(holder.options, event, con)
                    }
                } else {
                    holder.options.visibility = GONE
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList.get(position)) {
            is Event -> EVENTS
            else -> {
                OTHERS
            }
        }
    }

    fun changeList(list: List<Any>) {
        dataList = list
        if (dataList.size > duplicate.size) {
            alteredLists.clear()
            alteredLists.addAll(dataList)
            duplicate.clear()
            duplicate.addAll(dataList)
        }
        notifyDataSetChanged()

    }

    private fun createItemMenu(image: ImageView, any: Any, context: Context) {
        val popup = PopupMenu(context, image)
        popup.menuInflater.inflate(R.menu.announcment_item_menu, popup.menu)
        popup.setOnMenuItemClickListener { menuitem ->
            onItemClick?.let { }
            when (menuitem.itemId) {
                R.id.remove -> {
                    when (any) {
                        is Event -> {
                            onItemClick?.onRemove(any.eventID)
                        }
                        is Professor -> {
                            onItemClick?.onRemove(any.professorID.toInt())
                        }
                        is Student -> {
                            onItemClick?.onRemove(any.clg_num.toInt())
                        }
                        is Admin -> {
                            onItemClick?.onRemove(any.adminID)
                        }
                    }

                }
                R.id.edit -> {
                    when (any) {
                        is Event -> {
                            onItemClick?.edit(any.eventID.toString())
                        }
                        is Professor -> {
                            onItemClick?.edit(any.professorID.toString())
                        }
                        is Student -> {
                            onItemClick?.edit(any.clg_num)
                        }
                        is Admin -> {
                            onItemClick?.edit(any.adminID.toString())
                        }
                    }
                }
            }
            true
        }
        popup.show()
    }

    fun filter(newText: String?) {
        if (duplicate.isEmpty()) {
            return
        }
        if (newText == null || newText.trim().isEmpty()) {
            changeList(duplicate)
            return
        }
        scope.launch {
            val filtered: List<Any> = when (duplicate[0]) {
                is Event -> {
                    filterEventList(newText)
                }
                is Admin -> {
                    filterAdminList(newText)

                }
                is Professor -> {
                    filterProfessorList(newText)
                }
                is Student -> {
                    filterStudentList(newText)
                }

                else -> {
                    emptyList<Any>()
                }
            }
            withContext(Main) {
                changeList(filtered)
            }

        }
    }

    private fun filterEventList(newText: String): ArrayList<Event> {
        val eventList = duplicate as List<Event>
        val filteredList = ArrayList<Event>()
        for (item in eventList) {
            item.apply {
                if (eventTitle.lowercase()
                        .contains(newText.lowercase(Locale.getDefault())) || eventDescription
                        .lowercase(Locale.getDefault())
                        .contains(newText.lowercase()) || eventID.toString()
                        .contains(newText)
                ) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList
    }

    private fun filterAdminList(newText: String): ArrayList<Admin> {
        val adminList = duplicate as List<Admin>
        val filteredList = ArrayList<Admin>()
        for (item in adminList) {
            item.apply {
                if (name.contains(newText) || adminID.toString().contains(newText)) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList

    }

    private fun filterProfessorList(newText: String): ArrayList<Professor> {
        val professorList = duplicate as List<Professor>
        val filteredList = ArrayList<Professor>()
        for (item in professorList) {
            item.apply {
                if (firstName?.contains(newText) == true || lastName?.contains(newText) == true ||
                    professorID.toString().contains(newText)
                ) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList

    }

    private fun filterStudentList(newText: String): ArrayList<Student> {
        val studentList = duplicate as List<Student>
        val filteredList = ArrayList<Student>()
        for (item in studentList) {
            item.apply {
                if (firstName?.contains(newText) == true || lastName?.contains(newText) == true ||
                    clg_num.contains(newText) || univ_num.toString().contains(newText)
                ) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList

    }


    private val filteredList = object : Filter() {
        override fun performFiltering(textInput: CharSequence?): FilterResults {
            val filteredList = ArrayList<Any>()
            if ((textInput == null || textInput.trim()
                    .isEmpty() || duplicate.isEmpty()) && !changeFilter
            ) {
                filteredList.addAll(duplicate)
                Log.d(TAG, "performFiltering: okay 3")

            } else {
                Log.d(TAG, "performFiltering: okay")
                val text = textInput.toString().lowercase(Locale.getDefault()).trim()
                when (duplicate[0]) {
                    is Event -> {
                        val eventsList = duplicate as List<Event>
                        for (item in eventsList) {
                            item.apply {

                                if (eventTitle.lowercase().trim()
                                        .contains(text) || eventDescription.lowercase().trim()
                                        .contains(
                                            text
                                        ) || eventID.toString().lowercase().trim()
                                        .contains(text)
                                ) {
                                    filteredList.add(item)
                                }
                            }
                        }
                    }
                    is Admin -> {
                        val adminList = duplicate as List<Admin>
                        for (item in adminList) {
                            item.apply {
                                if (this.name.lowercase().trim()
                                        .contains(text) || adminID.toString()
                                        .lowercase().trim().contains(text)
                                ) {
                                    filteredList.add(item)
                                }
                            }
                        }

                    }
                    is Professor -> {
                        val professorList =  if(changeFilter){
                            alteredLists
                        }
                        else{
                            duplicate
                        } as List<Professor>


                        for (item in professorList) {
                            item.apply {
                                Log.d(TAG, "performFiltering: $name")
                                val fullName = "$firstName $lastName"

                                when (name) {
                                    Home.NAME -> {
                                        if (firstName?.lowercase()?.trim()
                                                ?.contains(text) == true || lastName?.lowercase()
                                                ?.trim()
                                                ?.contains(text) == true || fullName.trim()
                                                .lowercase().contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }
                                    Home.PROF_ID -> {
                                        if (professorID.toString().trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)

                                        }

                                    }
                                    Home.DEPART_ID -> {
                                        if (departmentID.toString().trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }
                                    Home.CLASS_ID -> {
                                        if (classID.toString().trim().lowercase().contains(text)) {
                                            filteredList.add(item)
                                        }
                                    }
                                    else -> {
                                        if (firstName?.lowercase()?.trim()
                                                ?.contains(text) == true ||
                                            lastName?.lowercase()?.trim()?.contains(text) == true ||
                                            classID.toString().trim().lowercase().contains(text) ||
                                            departmentID.toString().trim().lowercase()
                                                .contains(text) ||
                                            professorID.toString().trim().lowercase()
                                                .contains(text) || fullName.trim().lowercase()
                                                .contains(text)
                                        ) {
                                            Log.d(TAG, "performFiltering: 123 $item")
                                            filteredList.add(item)
                                        }
                                    }

                                }

                            }
                        }
                    }
                    is Student -> {
                        val studentList = duplicate as List<Student>
                        for (item in studentList) {
                            val other = item.other
                            item.apply {
                                val fullName = "$firstName $lastName"

                                when (name) {
                                    Home.NAME -> {

                                        if (firstName?.lowercase()?.trim()
                                                ?.contains(text) == true || lastName?.lowercase()
                                                ?.trim()
                                                ?.contains(text) == true || fullName.trim()
                                                .lowercase().contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }
                                    Home.ROLL_NUM -> {
                                        if (clg_num.trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)

                                        }
                                    }
                                    Home.HOD_ID -> {
                                        if (other?.hodID.toString().trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }
                                    Home.DEPART_ID -> {
                                        if (other?.departmentName.toString().trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }
                                    Home.PROF_ID -> {
                                        if (other?.professorID.toString().trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)

                                        }

                                    }

                                    Home.CLASS_ID -> {
                                        if (other?.classID.toString().trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }
                                    else -> {

                                        if (firstName?.lowercase()?.trim()
                                                ?.contains(text) == true || lastName?.lowercase()
                                                ?.trim()
                                                ?.contains(text) == true || other?.classID.toString()
                                                .trim().lowercase().contains(text)
                                            || other?.departmentName?.trim()?.lowercase()
                                                ?.contains(text) == true ||
                                            other?.professorID.toString().trim().lowercase()
                                                .contains(text)
                                            || other?.hodID.toString().trim().lowercase()
                                                .contains(text) || clg_num.trim().lowercase()
                                                .contains(text) || fullName.trim().lowercase()
                                                .contains(text)
                                        ) {
                                            filteredList.add(item)
                                        }
                                    }

                                }

                            }
                        }


                    }
                    else -> {
                        emptyList<Any>()
                    }

                }
            }
            val s = FilterResults()
            s.values = filteredList
            return s
        }

        override fun publishResults(textInput: CharSequence?, results: FilterResults?) {
            val res = results?.values as List<Any>
            alteredLists.clear()
            alteredLists.addAll(res)
            changeList(res)
        }
    }

    override fun getFilter(): Filter {
        return filteredList
    }

    fun changeSearchTerm(term: String) {
        this.name = term
    }

    fun getAlteredList(): List<Any> {
        return alteredLists
    }

    fun changeAnotherFilter(changeFilter: Boolean) {
        this.changeFilter = changeFilter
    }


}


