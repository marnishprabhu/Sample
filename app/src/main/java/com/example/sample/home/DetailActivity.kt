package com.example.sample.home

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sample.databinding.ActivityDetailBinding
import com.example.sample.home.detail.ProfessorDetailActivity
import com.example.sample.home.detail.StudentDetailActivity
import com.example.sample.room.entity.Admin
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.utils.Home
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    var currItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val extras = intent.extras
        currItem = extras?.get(Home.CURR_ITEM) as Int
        when (currItem) {
//            Home.ADMIN_TITLE -> {
//                val admin = extras.get(Home.SHOW_ALL) as Admin
//                fetchAdmin(admin)
//            }
            Home.PROF_TITLE -> {
                val prof = extras.get(Home.SHOW_ALL) as Professor
                fetchProfessors(prof)
            }
            Home.STUDENT_TITLE -> {
                val student = extras.get(Home.SHOW_ALL) as Student
                fetchStudents(student)
            }

        }
    }

    private fun fetchStudents(student: Student) {
        val intent = Intent(this,StudentDetailActivity::class.java)
        intent.putExtra(Home.SHOW_ALL,student)
        startActivity(intent)
        finish()

    }

    private fun fetchProfessors(prof: Professor) {
        val intent = Intent(this,ProfessorDetailActivity::class.java)
        intent.putExtra(Home.SHOW_ALL,prof)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp: 123 55555555555555")
        return super.onSupportNavigateUp()
    }

}