package com.example.sample.entry

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.home.MainActivity
import com.example.sample.room.entity.Admin
import com.example.sample.room.entity.Professor
import com.example.sample.utils.Testing
import com.example.sample.utils.collegeclasses.*
import com.example.sample.utils.professors.*
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class SplashScreenAppActivity : AppCompatActivity() {
    val appViewModel: AppViewModel by viewModels()
    val professorViewModel: ProfessorViewModel by viewModels()
    val studentViewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Log.d(TAG, "onCreate: marnish oncreate is called")


        lifecycleScope.launch(Dispatchers.IO) {
            val admins = appViewModel.getAllAdmins()
            if (admins.isEmpty()) {
                val admin = Admin(
                    "Walter chris", 123, 121, "pass"
                )
                appViewModel.addAdmin(admin)
            }
            val user = appViewModel.getUser()


            if (user != null) {
                if (appViewModel.isThemeChecked) {
                    return@launch
                }
                val savedMilli = user.todayDateInMilli
                val todayDateInMilli = Calendar.getInstance().timeInMillis

                if (savedMilli != null && savedMilli < todayDateInMilli) {
                    appViewModel.deleteFinishedEvents()

                }
                if (savedMilli != todayDateInMilli) {
                    user.todayDateInMilli = todayDateInMilli
                    appViewModel.changeUser(user)
                }

                appViewModel.user = user
                appViewModel.isThemeChecked = true
                setTheme()
                val intent = Intent(this@SplashScreenAppActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d(TAG, "onCreate: all the best")

                val classTable = appViewModel.getClassData()
                if (classTable.isEmpty()) {
                    // ADDING DEPARTMENTS
                    val res = Testing.createDepartment()
                    for (i in res) {
                        appViewModel.addDepartment(i)
                    }
                    // ADDING CLASSES

                    val cse = CSEClasses.createClasses()
                    for (i in cse) {
                        appViewModel.addClass(i)
                    }
                    val ece = ECEClasses.createClasses()
                    for (i in ece) {
                        appViewModel.addClass(i)
                    }
                    val eee = EEEClasses.createClasses()
                    for (i in eee) {
                        appViewModel.addClass(i)
                    }
                    val mech = MechClasses.createClasses()
                    for (i in mech) {
                        appViewModel.addClass(i)
                    }
                    val civil = CivilClasses.createClasses()
                    for (i in civil) {
                        appViewModel.addClass(i)
                    }

                    // ADDING PROFESSORS

                    val cseProf = CSEProfessors.createProfessors()
                    for (i in cseProf) {
                        val key = professorViewModel.addProfessor(i)
                        storeToClasses(i, key)


                    }
                    val eceProf = ECEProfessors.createProfessors()
                    for (i in eceProf) {
                        val key = professorViewModel.addProfessor(i)
                        storeToClasses(i, key)

                    }
                    val student = CSEProfessors.createAStudent()
                    studentViewModel.addStudent(student)

                    val eeeProf = EEEProfessors.createProfessors()
                    for (i in eeeProf) {
                        val key = professorViewModel.addProfessor(i)
                        storeToClasses(i, key)

                    }
                    val mechProf = MechProfessors.createProfessors()
                    for (i in mechProf) {
                        val key = professorViewModel.addProfessor(i)
                        storeToClasses(i, key)

                    }
                    val civilProf = CivilProfessors.createProfessors()
                    for (i in civilProf) {
                        val key = professorViewModel.addProfessor(i)
                        storeToClasses(i, key)

                    }

                }
                val intent = Intent(this@SplashScreenAppActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private suspend fun setTheme() {
        Log.d(TAG, "onCreate: marnish calling settehme done")
        withContext(Main) {
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            if (appViewModel.user!!.isDarkMode) {
                Log.d(TAG, "onCreate: marnish calling settehme done 12")

                if (nightMode != AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    Log.d(TAG, "onCreate: marnish calling settehme done 56")

                }
            }
        }


    }


    private fun storeToClasses(professor: Professor, key: Long) {
        val classIdForProf = professor.classID
        if (classIdForProf != null) {
            val classTable = appViewModel.getSingleClassData(classIdForProf)
            var profIDs = classTable.professorsIDs as ArrayList?
            if (profIDs == null) {
                profIDs = ArrayList<Int>()
            }
            profIDs.add(key.toInt())
            classTable.professorsIDs = profIDs
            appViewModel.updateProfessors(classTable)
            Log.d(TAG, "storeToClasses: $profIDs")
        }

    }
}