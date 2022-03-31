package com.example.sample.addacademicdata.add

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityAcademicEditBinding
import com.example.sample.room.entity.Student
import com.example.sample.utils.Home

class AcademicEditActivity : AppCompatActivity() {
    lateinit var binding:ActivityAcademicEditBinding
    lateinit var student :Student
    val studentViewModel:StudentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcademicEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent.extras
        if(extras?.get(Home.CURR_ITEM) != null){
            student = extras.get(Home.CURR_ITEM) as Student
            student.id = extras.get(Home.ID) as Int

            studentViewModel.student = student
        }
    }

}