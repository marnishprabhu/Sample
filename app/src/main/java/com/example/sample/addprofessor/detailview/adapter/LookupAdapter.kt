package com.example.sample.addprofessor.detailview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.addprofessor.detailview.LookupModel
import com.example.sample.databinding.ItemSelectDataBinding

class LookupAdapter(
    var list: ArrayList<LookupModel>,
    var onClickCard: onClickCard
) : RecyclerView.Adapter<LookupAdapter.LookupViewholder>() {


    class LookupViewholder(binding: ItemSelectDataBinding) : RecyclerView.ViewHolder(binding.root) {

        var content = binding.content
        var card = binding.card
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewholder {
        return LookupViewholder(
            ItemSelectDataBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LookupViewholder, position: Int) {
        val model = list[position]
        holder.content.text = model.text
        if(model.selected){
            holder.card.isChecked =true
        }
        holder.card.setOnClickListener {
            onClickCard.onClick(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun changeAdapter(list: ArrayList<LookupModel>, pos: Int?){
        this.list = list

        if(pos!=null){
            notifyItemChanged(pos)
        }
        else{
            notifyDataSetChanged()

        }

    }
}
interface onClickCard{
    fun onClick(pos: Int)
}