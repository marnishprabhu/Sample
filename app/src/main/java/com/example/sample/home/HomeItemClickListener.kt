package com.example.sample.home

import com.example.sample.bottomfragments.home.model.People

interface HomeItemClickListener {

    fun onViewAll(position:Int)
    fun onClick(dataList: List<People>, position: Int, type: Int)
}