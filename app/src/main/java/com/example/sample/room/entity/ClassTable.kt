package com.example.sample.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


@Entity
data class ClassTable(


    @PrimaryKey(autoGenerate = true)
    val classId: Int,
    val classYear: Int,
    val departmentID: Int,
    var professorsIDs:List<Int>? = null

)
class ProfessorsIDConverter {
    @TypeConverter
    fun storedStringToMyObjects(data: String?):List<Int?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson<List<Int>?>(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects:List<Int>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}
