package com.example.sample.bottomfragments.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.*
import com.example.sample.bottomfragments.profile.model.Item
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.room.entity.HandlingClass
import com.example.sample.room.entity.Qualification
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProfileAdapter(
    var profileList: ArrayList<ProfileItems>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val QUALIFICATION_ID = 1
    val NORMAL_ID = 2
    val HANDLING_CLASS_ID = 3


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            NORMAL_ID->{
                val view = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ProfileViewholder(view)
            }
            QUALIFICATION_ID->{
                val view = ItemProfileQualificationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ProfileQualificationViewholder(view)
            }
            HANDLING_CLASS_ID->{
                val view = ItemProfileHandlingclassesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ProfileHandlingClassesViewholder(view)
            }
            else->{
                throw Exception()
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ProfileViewholder->{
                val item = profileList.get(position) as Item<Any>
                holder.key.text =item.key
                holder.value.text = item.value.toString()
            }
            is ProfileQualificationViewholder->{
                val qualification = profileList.get(position) as Qualification
                holder.courseName.text = qualification.courseName
                holder.coursePercent.text = qualification.coursePercentage.toString()
                holder.coursePassYear.text  =qualification.passingYear.toString()

            }
            is ProfileHandlingClassesViewholder->{
                val handlingClass = profileList.get(position) as HandlingClass
                holder.classID.text = handlingClass.classID.toString()
                holder.subName.text = handlingClass.subName
            }
        }

    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(profileList[position]){
            is Qualification-> QUALIFICATION_ID
            is HandlingClass-> HANDLING_CLASS_ID
            else-> NORMAL_ID
        }
    }


    fun changeList(changedList: ArrayList<ProfileItems>) {

        this.profileList = changedList
        notifyDataSetChanged()
    }

    class ProfileViewholder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var key = binding.key
        var value = binding.value
    }

    class ProfileQualificationViewholder(val binding: ItemProfileQualificationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var courseName = binding.courseName
        var coursePercent = binding.coursePercentage
        var coursePassYear = binding.coursePassYear
    }
    class ProfileHandlingClassesViewholder(val binding: ItemProfileHandlingclassesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var classID = binding.classId
        var subName = binding.subName
    }

}