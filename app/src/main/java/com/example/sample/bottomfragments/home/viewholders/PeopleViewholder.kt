package com.example.sample.bottomfragments.home.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemPeopleCardviewBinding
import com.example.sample.databinding.ItemPeopleHomeBinding

class PeopleViewholder(binding: ItemPeopleCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
    var recyclerView = binding.horizontalRecView
    var cardView = binding.peopleCardview

}

class EachPeopleViewholder(binding: ItemPeopleHomeBinding) : RecyclerView.ViewHolder(binding.root) {
    var profile = binding.profilePic
    var name = binding.name
    var id = binding.clgId
    var cardview = binding.cardview
}