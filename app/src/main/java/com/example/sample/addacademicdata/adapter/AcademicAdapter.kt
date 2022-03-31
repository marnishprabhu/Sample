package com.example.sample.addacademicdata.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.databinding.ItemViewAllBinding
import com.example.sample.home.adapter.ViewAllAdapter
import com.example.sample.room.entity.Student

class AcademicAdapter
    (var data:ArrayList<Student>,
     var onStudentAcademicClicked: OnStudentAcademicClicked)
    : RecyclerView.Adapter<ViewAllAdapter.ItemViewAllViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewAllAdapter.ItemViewAllViewHolder {
      return ViewAllAdapter.ItemViewAllViewHolder(
          ItemViewAllBinding.inflate(LayoutInflater.from(
              parent.context
          ),parent,false)
      )
    }

    override fun onBindViewHolder(holder: ViewAllAdapter.ItemViewAllViewHolder, position: Int) {
        val student = data[position]
        holder.id.text = student.clg_num
        val fullName = "${student.firstName} ${student.lastName}"
        holder.name.text = fullName
        holder.deptId.text = student.other?.departmentName
        holder.msg.visibility = GONE
        holder.options.visibility = GONE
        holder.card.setOnClickListener{
            onStudentAcademicClicked.onClick(position)
        }
        holder.profile.setImageResource(R.drawable.students_new)

    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun changeList(changedList:ArrayList<Student>){
        this.data = ArrayList(changedList)
        notifyDataSetChanged()
    }

}
interface OnStudentAcademicClicked{
    fun onClick(position:Int)
}