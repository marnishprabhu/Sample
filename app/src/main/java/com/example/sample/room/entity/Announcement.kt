package com.example.sample.room.entity

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.sample.bottomfragments.home.model.HomeItems


@Entity
data class Announcement(
    val visibilityID: Int,
    val uniqueId: String?,
    val title: String,
    val description: String
) : HomeItems() {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @Ignore
    val imageView: Drawable? = null
}
