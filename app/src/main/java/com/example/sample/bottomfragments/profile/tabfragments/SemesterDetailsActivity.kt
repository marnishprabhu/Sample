package com.example.sample.bottomfragments.profile.tabfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.sample.R
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivitySemesterDetailsBinding
import com.example.sample.room.entity.Student
import com.example.sample.utils.Home

class SemesterDetailsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySemesterDetailsBinding
    val studentViewModel: StudentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySemesterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        extras?.let {
            val student = it.get(Home.CURR_ITEM) as Student
            val studentId  = it.get(Home.ID)
            student.id = studentId as Int
            studentViewModel.student = student
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }



}