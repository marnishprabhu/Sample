package com.example.sample.unfinished.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemUnfinishedBinding
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Constants

class UnfinishedAdapter(
    var data: List<Any>,
    var listener: UnfinishedCompleteNow
) :
    RecyclerView.Adapter<UnfinishedAdapter.UnfinishedViewHolder>() {


    class UnfinishedViewHolder(binding: ItemUnfinishedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var name = binding.name
        var number = binding.uniqueNumber
        var complete = binding.complete
        var remove = binding.remove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnfinishedViewHolder {
        return UnfinishedViewHolder(
            ItemUnfinishedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UnfinishedViewHolder, position: Int) {
        val data = data[position]
        when (data) {
            is Student -> {
                holder.name.text = "${data.firstName} ${data.lastName}"
                holder.number.text = data.univ_num.toString()
                holder.complete.setOnClickListener {
                    listener.onCompleteNow(position, Constants.STUDENT)
                }
                holder.remove.setOnClickListener {
                    listener.onRemove(position,Constants.STUDENT)
                }

            }
            is Professor -> {
                holder.name.text = "${data.firstName} ${data.lastName}"
                holder.number.text = data.professorID.toString()
                holder.complete.setOnClickListener {
                    listener.onCompleteNow(position,Constants.PROFESSOR)
                }
                holder.remove.setOnClickListener {
                    listener.onRemove(position,Constants.PROFESSOR)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun changeList(changedList:ArrayList<Any>){
        this.data =  changedList
        Log.d(TAG, "changeList: ${data.size}")
        notifyDataSetChanged()
    }
}

interface UnfinishedCompleteNow {
    fun onCompleteNow(position: Int,type:Int)
    fun onRemove(position: Int,type: Int)
}