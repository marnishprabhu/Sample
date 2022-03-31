package com.example.sample.widget

import android.app.Application
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View.VISIBLE
import android.widget.RemoteViews
import com.example.sample.R
import com.example.sample.addannouncement.AddAnnouncement
import com.example.sample.addprofessor.AddProfessor
import com.example.sample.addstudent.AddStudent
import com.example.sample.leave.ApplyLeaveActivity
import com.example.sample.leave.ShowLeaveActivity
import com.example.sample.repository.AppRepository
import com.example.sample.repository.ProfessorRepository
import com.example.sample.repository.StudentRepository
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CMSWidgetProvider
    : AppWidgetProvider() {

    lateinit var studentRepository: StudentRepository
    lateinit var professorRepository: ProfessorRepository
    lateinit var appRepository: AppRepository
    var scope = CoroutineScope(Dispatchers.IO)
    var studentSize = 0
    var professorSize = 0
    var user: User? = null

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        Log.d(TAG, "onUpdate: changeAppWidget ")
        val activity = context!!.applicationContext!! as Application

        studentRepository = StudentRepository(
            activity
        )
        professorRepository = ProfessorRepository(
            activity
        )
        appRepository = AppRepository(activity)
        scope.launch {

            user = appRepository.getUser()
            if (user == null) {
                return@launch
            }
            studentSize = studentRepository.getOnlyFinishedStudents().size
            professorSize = professorRepository.getOnlyFinishedProfessors().size
            val remoteViews = RemoteViews(
                context.packageName,
                R.layout.widget_cms
            )

            withContext(Main) {
                if (appWidgetIds != null) {
                    when (user!!.loginID) {

                        Constants.ADMIN -> {

                            for (id in appWidgetIds) {
                                setIconsForAdmin(id, context, appWidgetManager, remoteViews)
                            }

                        }
                        Constants.PROFESSOR -> {
                            for (id in appWidgetIds) {
                                setIconsForProfessor(id, context, appWidgetManager, remoteViews)
                            }
                        }
                        Constants.STUDENT -> {
                            for (id in appWidgetIds) {
                                setIconsForStudent(id, context, appWidgetManager, remoteViews)
                            }
                        }

                    }


                }
            }

        }

    }


    private fun setIconsForStudent(
        id: Int,
        context: Context,
        appWidgetManager: AppWidgetManager?,
        remoteViews: RemoteViews
    ) {
        scope.launch {
            val student = studentRepository.getStudent(user!!.identifier)
            Log.d(TAG, "setIconsForStudent: ${student!!.id}")
            withContext(Main) {
                val showLeaveIntent = Intent(context, ShowLeaveActivity::class.java)
                showLeaveIntent.action = System.currentTimeMillis().toString()
                showLeaveIntent.putExtra(Home.ID, user!!.loginID)
                showLeaveIntent.putExtra(Home.CURR_ITEM, user!!.identifier)
                showLeaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                val showLeavePendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    showLeaveIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                val applyLeaveIntent = Intent(context, ApplyLeaveActivity::class.java)
                applyLeaveIntent.putExtra(Home.SHOW_ALL, student)
                applyLeaveIntent.putExtra(Home.ID, student.id)
                applyLeaveIntent.action = System.currentTimeMillis().toString()

                applyLeaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                val applyLeavePendingIntent = PendingIntent.getActivity(
                    context,
                    121,
                    applyLeaveIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

//            val remoteViews = RemoteViews(
//                context.packageName,
//                R.layout.widget_cms
//            )
                remoteViews.setViewVisibility(
                    R.id.student_layout,
                    VISIBLE
                )

                remoteViews.setOnClickPendingIntent(
                    R.id.student_apply_leave,
                    applyLeavePendingIntent
                )
                remoteViews.setOnClickPendingIntent(
                    R.id.student_show_leave,
                    showLeavePendingIntent
                )
                appWidgetManager?.updateAppWidget(id, remoteViews)
            }
        }


    }

    private fun setIconsForProfessor(
        id: Int,
        context: Context,
        appWidgetManager: AppWidgetManager?,
        remoteViews: RemoteViews
    ) {
        Log.d(TAG, "setIconsForProfessor: 12")
        val addStudentIntent = Intent(context, AddStudent::class.java)
        addStudentIntent.action = System.currentTimeMillis().toString()
        addStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentStudent = PendingIntent.getActivity(
            context,
            123,
            addStudentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val addAnnouncementIntent = Intent(context, AddAnnouncement::class.java)
        addAnnouncementIntent.action = System.currentTimeMillis().toString()
        addAnnouncementIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentAnnouncement = PendingIntent.getActivity(
            context,
            0,
            addAnnouncementIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val showLeaveIntent = Intent(context, ShowLeaveActivity::class.java)
        showLeaveIntent.action = System.currentTimeMillis().toString()
        showLeaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        showLeaveIntent.putExtra(Home.ID, user!!.loginID)
        showLeaveIntent.putExtra(Home.CURR_ITEM, user!!.identifier)
        val showLeavePendingIntent = PendingIntent.getActivity(
            context,
            0,
            showLeaveIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


//    val remoteViews = RemoteViews(
//        context.packageName,
//        R.layout.widget_cms
//    )
        remoteViews.setViewVisibility(
            R.id.professor_layout,
            VISIBLE
        )
        remoteViews.setOnClickPendingIntent(
            R.id.professor_add_student,
            pendingIntentStudent
        )
        remoteViews.setOnClickPendingIntent(
            R.id.prof_show_leaves,
            showLeavePendingIntent
        )

        remoteViews.setOnClickPendingIntent(
            R.id.prof_add_announcement,
            pendingIntentAnnouncement
        )

        remoteViews.setTextViewText(
            R.id.text_no_of_prof,
            professorSize.toString()
        )
        remoteViews.setTextViewText(
            R.id.text_no_of_students,
            studentSize.toString()
        )
        appWidgetManager?.updateAppWidget(id, remoteViews)
    }

    private fun setIconsForAdmin(
        id: Int,
        context: Context,
        appWidgetManager: AppWidgetManager?,
        remoteViews: RemoteViews
    ) {
        val addStudentIntent = Intent(context, AddStudent::class.java)
        addStudentIntent.action = System.currentTimeMillis().toString()
        addStudentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentStudent = PendingIntent.getActivity(
            context,
            0,
            addStudentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val addProfessorIntent = Intent(context, AddProfessor::class.java)
        addProfessorIntent.action = System.currentTimeMillis().toString()
        addProfessorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentProfessor = PendingIntent.getActivity(
            context,
            0,
            addProfessorIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val addAnnouncementIntent = Intent(context, AddAnnouncement::class.java)
        addAnnouncementIntent.action = System.currentTimeMillis().toString()
        addAnnouncementIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntentAnnouncement = PendingIntent.getActivity(
            context,
            0,
            addAnnouncementIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

//
//    val remoteViews = RemoteViews(
//        context.packageName,
//        R.layout.widget_cms
//    )
        remoteViews.setViewVisibility(
            R.id.admin_layout,
            VISIBLE
        )
        remoteViews.setOnClickPendingIntent(
            R.id.admin_add_student,
            pendingIntentStudent
        )
        remoteViews.setOnClickPendingIntent(
            R.id.admin_add_announcement,
            pendingIntentAnnouncement
        )
        remoteViews.setOnClickPendingIntent(
            R.id.admin_add_professor,
            pendingIntentProfessor
        )

        remoteViews.setTextViewText(
            R.id.text_no_of_prof,
            professorSize.toString()
        )
        remoteViews.setTextViewText(
            R.id.text_no_of_students,
            studentSize.toString()
        )
        appWidgetManager?.updateAppWidget(id, remoteViews)
    }
}