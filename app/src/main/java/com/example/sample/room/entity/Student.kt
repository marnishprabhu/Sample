package com.example.sample.room.entity

import android.os.Parcelable
import androidx.room.*
import com.example.sample.addacademicdata.model.AllSemesters
import com.example.sample.bottomfragments.profile.model.ProfileItems

import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.lang.reflect.Type


@Entity
@Parcelize
data class Student(

    var clg_num: String,
    var univ_num: Long,
    var firstName: String?,
    var lastName: String?,
    var age: Int?,
    var gender: String?,
    var bloodGrp: String?,
    var phoneNumber: Long?,
    var gmail: String?,
    var dateOfBirth: String?,
    @Embedded
    var address: @RawValue Address? = null,
    @Embedded
    var family: @RawValue Family? = null,
    var qualifications: @RawValue List<Qualification>?,
    @Embedded
    var other: @RawValue OtherDetails? = null,
    var semesterData: @RawValue AllSemesters? = null,
    var announcementsSize: Int = 0,
    var leaveRequestSize: Int = 0,
    var noOfReceivedMessages: Int = 0

) : Actor(),
    Parcelable {


    @PrimaryKey(autoGenerate = true)
    var id = 0

    constructor(univ_num: Long) : this(
        "", univ_num = univ_num,
        null, null, null, null, null, null, null,
        null, null, null, ArrayList<Qualification>(), null, null
    )
}

@Parcelize
data class Address(
    val houseNumber: String,
    val streetName: String,
    val city: String,
    val pincode: Int
) : Parcelable

@Parcelize
data class Family(
    val fatherName: String,
    val fatherOccupation: String?,
    val fatherPhoneNumber: Long,
    val motherName: String,
    val motherOccupation: String?,
    val motherPhoneNumber: Long,
    val noOfSiblings: Int?
) : Parcelable

@Parcelize
data class Qualification(
    var courseName: String,
    var coursePercentage: Float,
    var passingYear: Int
) : ProfileItems(), Parcelable

@Parcelize
data class OtherDetails(
    val courseName: String,
    val year: Int,
    val departmentName: String,
    val batchFrom: Int,
    val batchTo: Int,
    val classID: Long,
    val hodID: Int,
    val professorID: Int,
    val password: String
) : Parcelable

class QualificationConverter {
    @TypeConverter
    fun storedStringToMyObjects(data: String?): List<Qualification?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Qualification?>?>() {}.type
        return gson.fromJson<List<Qualification?>>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<Qualification?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}

open class Actor
