package com.example.sample.message

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.sample.room.entity.HandlingClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

//
//@Parcelize
//data class EachMessage(
//    var milliSecond:Long,
//    var message:String,
//    var isSend:Boolean
//) : Parcelable
//
//@Parcelize
//data class AllMessages(
//    var identificationId:String,
//    var milliSecond: Long,
//    var messages:@RawValue ArrayList<EachMessage>,
//    var name:String
//) : Parcelable
//@Parcelize
//data class UserMessages(
//    var userMessages: @RawValue ArrayList<AllMessages>
//
//) : Parcelable
//
//class EachMesssageConverter {
//    @TypeConverter
//    fun storedStringToMyObjects(data: String?): List<EachMessage?>? {
//        val gson = Gson()
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType: Type = object : TypeToken<List<EachMessage?>?>() {}.type
//        return gson.fromJson<List<EachMessage?>>(data, listType)
//    }
//
//    @TypeConverter
//    fun myObjectsToStoredString(myObjects: List<EachMessage?>?): String? {
//        val gson = Gson()
//        return gson.toJson(myObjects)
//    }
//}
//
//class UserMesssageConverter {
//    @TypeConverter
//    fun storedStringToMyObjects(data: String?): UserMessages? {
//        val gson = Gson()
//        if (data == null) {
//            return null
//        }
//        val listType: Type = object : TypeToken<List<EachMessage?>?>() {}.type
//        return gson.fromJson<UserMessages>(data, listType)
//    }
//
//    @TypeConverter
//    fun myObjectsToStoredString(myObjects: UserMessages?): String? {
//        val gson = Gson()
//        return gson.toJson(myObjects)
//    }
//}
//
//
//class AllMesssageConverter {
//    @TypeConverter
//    fun storedStringToMyObjects(data: String?): List<AllMessages?>? {
//        val gson = Gson()
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType: Type = object : TypeToken<List<AllMessages?>?>() {}.type
//        return gson.fromJson<List<AllMessages?>>(data, listType)
//    }
//
//    @TypeConverter
//    fun myObjectsToStoredString(myObjects: List<AllMessages?>?): String? {
//        val gson = Gson()
//        return gson.toJson(myObjects)
//    }
//}