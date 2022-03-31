package com.example.sample.room.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.lang.reflect.Type
import java.util.*


@Entity
@Parcelize
data class Professor(
    @PrimaryKey(autoGenerate = true)
    var professorID:Int = 0,
    var firstName: String?,
    var lastName: String?,
    var age: Int?,
    var gender: String?,
    var dateOfBirth: String?,
    var bloodGrp: String?,
    var phoneNumber: Long?,
    var gmail: String?,
    @Embedded
    var address: @RawValue Address? = null,
    @Embedded
    var family: @RawValue Family? = null,
    var qualifications: @RawValue List<Qualification>?,
    var handlingClasses: @RawValue List<HandlingClass>?,
    var classID: Int?,

    var password: String?,
    var departmentID: Int?,
    var hodID: Int?,
    var announcementsSize:Int = 0,
    var leaveRequestSize :Int=0,
    var noOfReceivedMessages:Int = 0


) : Actor(), Parcelable {
    constructor(firstName: String?) : this(0,
        firstName, null, null, null,
        null, null, null, null,
        null, null, null, null, null, null,
        null, null
    )


}

@Parcelize
data class HandlingClass(
    val classID: Int,
    val subName: String
) : ProfileItems(), Parcelable

class HandlingClassConverter {
    @TypeConverter
    fun storedStringToMyObjects(data: String?): List<HandlingClass?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<HandlingClass?>?>() {}.type
        return gson.fromJson<List<HandlingClass?>>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<HandlingClass?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}
