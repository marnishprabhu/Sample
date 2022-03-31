package com.example.sample.unfinished

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityEditUnfinishedBinding
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Home

class EditUnfinishedActivity : AppCompatActivity() {
    private lateinit var navControllerStudent: NavController
    private lateinit var navControllerProfessor: NavController

    val studentViewModel: StudentViewModel by viewModels()
    val professorViewModel: ProfessorViewModel by viewModels()
    lateinit var binding: ActivityEditUnfinishedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUnfinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val navHostFragmentStudent =
            supportFragmentManager.findFragmentById(binding.navHostFragmentEditStudent.id) as NavHostFragment
        navControllerStudent = navHostFragmentStudent.navController


        val navHostFragmentProfessor =
            supportFragmentManager.findFragmentById(binding.addProfessorNavHost.id) as NavHostFragment
        navControllerProfessor = navHostFragmentProfessor.navController

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent.extras
        if (extras != null) {
            val item = extras.get(Home.CURR_ITEM)
            if (item is Student) {
                fetchStudent(item, extras.get(Home.ID)!! as Int)
            } else if (item is Professor) {
                fetchProfessor(item)
            }
        }
    }

    private fun fetchProfessor(professor: Professor) {
        binding.addProfessorNavHost.visibility = VISIBLE
        binding.navHostFragmentEditStudent.visibility = GONE
        professorViewModel.professor = professor

        val address = professor.address
        val family = professor.family
        val qualification = professor.qualifications
        val handlingClass = professor.handlingClasses

        if (address == null) {
            navControllerProfessor.navigate(R.id.action_professorFirst_to_professorSecond)

        } else if (family == null) {
            navControllerProfessor.navigate(R.id.action_professorFirst_to_professorThird)


        } else if (qualification == null || qualification.isEmpty()) {
            navControllerProfessor.navigate(R.id.action_professorFirst_to_professorFourth)


        } else if (handlingClass == null || handlingClass.isEmpty()) {
            navControllerProfessor.navigate(R.id.action_professorFirst_to_professorFifth)


        } else {
            Log.d(TAG, "fetchProfessor: navigating")
            navControllerProfessor.navigate(R.id.action_professorFirst_to_professorSixth)
        }
    }

    private fun fetchStudent(student: Student, id: Int) {
        binding.navHostFragmentEditStudent.visibility = VISIBLE
        binding.addProfessorNavHost.visibility = GONE

        student.id = id
        studentViewModel.student = student


        val address = student.address
        val family = student.family
        val qualification = student.qualifications

        if (address == null) {
            Log.d(TAG, "fetchStudent: navigating")
            navControllerStudent.navigate(R.id.action_secondFragment_to_thirdFragment)

        } else if (family == null) {
            navControllerStudent.navigate(R.id.action_secondFragment_to_fourthFragment2)


        } else if (qualification == null || qualification.isEmpty()) {
            navControllerStudent.navigate(R.id.action_secondFragment_to_fifthFragment)


        } else {
            navControllerStudent.navigate(R.id.action_secondFragment_to_sixthFragment)
        }
    }

    override fun onBackPressed() {
        finish()
        Log.d(TAG, "onBackPressed: called success")
    }

}