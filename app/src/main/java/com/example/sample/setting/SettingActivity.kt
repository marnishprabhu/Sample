package com.example.sample.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivitySettingBinding
import com.example.sample.entry.LoginActivity
import com.example.sample.room.entity.Admin
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.ProfileValues
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import android.content.ComponentName

import android.widget.RemoteViews

import android.appwidget.AppWidgetManager
import android.content.ContentValues.TAG
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.sample.widget.CMSWidgetProvider


class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    val studentViewModel: StudentViewModel by viewModels()
    val professorViewModel: ProfessorViewModel by viewModels()
    val appViewModel: AppViewModel by viewModels()
    lateinit var user: User
    lateinit var admin: Admin
    var nooOfStudentsSize = 0
    var noOfProfessorsSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.changeThemeCard.setOnClickListener {
            val intent = Intent(this, ChangeThemeActivity::class.java)
            startActivity(intent)
        }
        Log.d(TAG, "onCreate: on old ${ProfileValues.collegeId}")
        Log.d(TAG, "onCreate: on old ${ProfileValues.name}")
        Log.d(TAG, "onCreate: on old ${ProfileValues.uniqueId}")


        if (ProfileValues.collegeId.trim().isNotEmpty() && ProfileValues.name.trim().isNotEmpty()
            && ProfileValues.uniqueId.trim().isNotEmpty()
        ) {
            Log.d(TAG, "onCreate: on old")
            binding.apply {
                name.text = ProfileValues.name
                uniqueId.text = ProfileValues.uniqueId
                clgId.text = ProfileValues.collegeId
                noOfStudents.text = ProfileValues.noOfStudents.toString()
                noOfProfessors.text = ProfileValues.noOfProfessors.toString()
                when (ProfileValues.loginId) {
                    Constants.ADMIN -> {
                        proPic.setImageResource(R.drawable.professor)
                    }
                    Constants.PROFESSOR -> {
                        proPic.setImageResource(R.drawable.teachings)
                    }
                    Constants.STUDENT -> {
                        proPic.setImageResource(R.drawable.students_new)
                    }
                }
            }
        } else {
            lifecycleScope.launch(IO) {
                user = appViewModel.getUser()!!
                ProfileValues.loginId = user.loginID
                fetchTop()
                fetchCollegeInformations()
            }
        }


        binding.logOut.setOnClickListener {
            AlertDialog.Builder(this, com.example.sample.R.style.AlertDialogboxTheme)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->

                    lifecycleScope.launch(IO) {
                        changeAppWidget()
                        Log.d(TAG, "onCreate: backing changed")

                        appViewModel.clearApp()
                        ProfileValues.clear()
                        withContext(Main) {
                            Log.d(TAG, "backing..")
                            val intent =
                                Intent(this@SettingActivity, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                            Log.d(TAG, "onCreate: backing done")
                        }
                    }
                }
                .setNegativeButton("No") { _, _ ->
                    {

                    }
                }.create().show()
        }
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun changeAppWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(packageName, com.example.sample.R.layout.widget_cms)
        val thisWidget = ComponentName(this, CMSWidgetProvider::class.java)
        remoteViews.setViewVisibility(R.id.student_layout,GONE)
        remoteViews.setViewVisibility(R.id.professor_layout,GONE)
        remoteViews.setViewVisibility(R.id.admin_layout,GONE)
        remoteViews.setViewVisibility(R.id.login, VISIBLE)

        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    private suspend fun fetchTop() {
        lifecycleScope.launch(IO) {
            admin = appViewModel.getAllAdmins()[0]
            ProfileValues.collegeId = admin.collegeID.toString()
        }
        val loginID = user.loginID
        withContext(Main) {
            binding.clgId.text = admin.collegeID.toString()
            when (loginID) {
                Constants.ADMIN -> {
                    withContext(Main) {
                        binding.name.text = admin.name
                        binding.uniqueId.text = admin.adminID.toString()
                        binding.proPic.setImageResource(R.drawable.professor)

                        ProfileValues.apply {
                            uniqueId = admin.adminID.toString()
                            name = admin.name
                        }
                    }
                    binding.proPic.setImageResource(com.example.sample.R.drawable.professor)
                }
                Constants.PROFESSOR -> {
                    binding.proPic.setImageResource(com.example.sample.R.drawable.teachings)
                    lifecycleScope.launch(IO) {
                        val prof = professorViewModel.getProfessor(user.identifier.toInt())
                        var name_ = ""
                        withContext(Main) {
                            name_ = "${prof!!.firstName} ${prof.lastName}"
                            binding.name.text = name_
                            binding.uniqueId.text = prof.professorID.toString()
                            binding.proPic.setImageResource(R.drawable.teachings)

                        }

                        ProfileValues.apply {
                            name = name_
                            this.uniqueId = prof!!.professorID.toString()
                        }
                    }
                }
                Constants.STUDENT -> {
                    binding.proPic.setImageResource(com.example.sample.R.drawable.students_new)
                    lifecycleScope.launch(IO) {
                        val student = studentViewModel.getStudent(user.identifier)
                        withContext(Main) {
                            val name_ = "${student!!.firstName} ${student.lastName}"

                            binding.name.text = name_
                            binding.uniqueId.text = student.clg_num
                            binding.proPic.setImageResource(R.drawable.students_new)

                            ProfileValues.apply {
                                name = name_
                                this.uniqueId = student.clg_num
                            }
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    private suspend fun fetchCollegeInformations() {
         nooOfStudentsSize = studentViewModel.getOnlyFinishedStudents().size

        ProfileValues.noOfStudents = nooOfStudentsSize
        noOfProfessorsSize = professorViewModel.getOnlyFinishedProfessors().size
        ProfileValues.noOfProfessors = noOfProfessorsSize

        withContext(Main) {
            binding.apply {
                noOfProfessors.text = noOfProfessorsSize.toString()
                noOfStudents.text = nooOfStudentsSize.toString()
            }
        }


    }
}