package com.example.sample.bottomfragments.home.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemTitleHomeBinding

class TitleViewholder(binding:ItemTitleHomeBinding): RecyclerView.ViewHolder(binding.root) {

    var title = binding.title
    var viewAll = binding.viewAll
    var divider = binding.divider
    var image = binding.image
    var number =binding.number
    var top = binding.unifinished
}