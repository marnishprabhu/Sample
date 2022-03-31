package com.example.sample.home

import com.example.sample.room.entity.Actor

interface OnMessageClickListener {
    fun onClick(actor: Actor)
}