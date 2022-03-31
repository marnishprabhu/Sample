package com.example.sample.room.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize


@Entity

@Parcelize
data class User(
    var loginID: Int,
    var identifier: String,
    var isDarkMode: Boolean = false,
    var todayDateInMilli: Long? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
