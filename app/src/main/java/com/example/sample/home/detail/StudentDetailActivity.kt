package com.example.sample.home.detail

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityStudentDetailBinding
import com.example.sample.home.detail.student.MainStudentDetailFragment
import com.example.sample.home.edit.EditViewAllActivity
import com.example.sample.room.entity.Student
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentDetailActivity : AppCompatActivity() {
    private lateinit var delete: MenuItem
    lateinit var binding: ActivityStudentDetailBinding
    val studentViewModel: StudentViewModel by viewModels()
    val appViewModel: AppViewModel by viewModels()
    lateinit var edit: MenuItem
    lateinit var user: User
    lateinit var student: Student
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: detail is done")
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        student = intent.extras?.get(Home.SHOW_ALL) as Student
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment,
            MainStudentDetailFragment(student)
        ).commit()

    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp: navigate")
        onBackPressed()
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        lifecycleScope.launch(IO) {
            user = appViewModel.getUser()!!

            withContext(Main) {
                if (user.loginID == Constants.ADMIN) {

                    menuInflater.inflate(R.menu.announcment_item_menu, menu)
                    menu.findItem(R.id.remove).setOnMenuItemClickListener {
                        removeStudent(student.clg_num.toInt())
                        return@setOnMenuItemClickListener true
                    }
                    menu.findItem(R.id.edit).setOnMenuItemClickListener {
                        edit(student.clg_num)
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
        return true
    }

    private fun removeStudent(id: Int) {
        lifecycleScope.launch(IO) {
            studentViewModel.removeStudent(id)
            withContext(Main) {
                Toast.makeText(
                    this@StudentDetailActivity,
                    "Student  Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
                super.onBackPressed()
            }
        }
    }

    private fun edit(id: String) {
        Log.d(TAG, "edit: $id")
        lifecycleScope.launch(IO) {
            val intent = Intent(this@StudentDetailActivity, EditViewAllActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, Home.STUDENT_TITLE)
            intent.putExtra(Home.SHOW_ALL, id)
            startActivity(intent)
        }
    }
}