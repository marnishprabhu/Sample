package com.example.sample.addacademicdata.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.sample.bottomfragments.profile.model.ProfileItems
import com.example.sample.room.entity.HandlingClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type
import java.util.*


@Parcelize
data class Semester(
    var subName:String,
    var grade:String

): ProfileItems(), Parcelable

@Parcelize
data class SemesterList(
    val semesterList : List<Semester>
):Parcelable

@Parcelize
data class AllSemesters(
    var semestersMarks:HashMap<String,SemesterList>?
) : Parcelable




class SemesterMapConverter{
    @TypeConverter
    fun storedStringToMyObjects(data: String?):AllSemesters? {
        val gson = Gson()
        if (data == null) {
            return AllSemesters(null)
        }
        val listType: Type = object : TypeToken<AllSemesters?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: AllSemesters?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}
class SemesterListConverter{
    @TypeConverter
    fun storedStringToMyObjects(data: String?): List<Semester?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Semester?>?>() {}.type
        return gson.fromJson<List<Semester?>>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<Semester?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}
