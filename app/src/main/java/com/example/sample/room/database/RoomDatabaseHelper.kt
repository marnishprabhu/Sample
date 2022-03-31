package com.example.sample.room.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sample.addacademicdata.model.SemesterListConverter
import com.example.sample.addacademicdata.model.SemesterMapConverter
import com.example.sample.leave.Leave
import com.example.sample.room.dao.*
import com.example.sample.room.entity.*


@Database(
    entities = [Student::class, Professor::class, Announcement::class,
        User::class, Event::class, Admin::class, Department::class
               ,ClassTable::class,Leave::class,AllMessage::class,MessageSize::class],
    version = 13
)
@TypeConverters(
    QualificationConverter::class, HandlingClassConverter::class,
    SemesterMapConverter::class, SemesterListConverter::class,
    ProfessorsIDConverter::class
)
abstract class RoomDatabaseHelper : RoomDatabase() {

    abstract fun studentDao(): StudentDAO
    abstract fun professorDao(): ProfessorDAO
    abstract fun announcementDao(): AnnouncementDAO
    abstract fun appDao(): AppDAO
    abstract fun adminDao(): AdminDAO
    abstract fun eventDao(): EventDAO
    abstract fun department(): DepartmentDao
    abstract  fun classDao():ClassDao
    abstract fun leaveDao():LeaveDao
    abstract fun allMessageDao():AllMessageDao
    abstract fun messageSizeDao():MessageSizeDAO

    companion object {
        var instance: RoomDatabaseHelper? = null
        fun getInstance(application: Application): RoomDatabaseHelper {
            return if (instance == null) {
                instance = Room.databaseBuilder(
                    application.applicationContext,
                    RoomDatabaseHelper::class.java,
                    "Application Info"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance as RoomDatabaseHelper
            } else {
                instance as RoomDatabaseHelper
            }
        }
    }
}