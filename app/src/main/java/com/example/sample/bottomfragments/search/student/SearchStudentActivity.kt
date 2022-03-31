package com.example.sample.bottomfragments.search.student

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.announcement.OnItemClick
import com.example.sample.databinding.ActivitySearchStudentBinding
import com.example.sample.home.DetailActivity
import com.example.sample.home.adapter.ViewAllAdapter
import com.example.sample.home.edit.EditViewAllActivity
import com.example.sample.room.entity.Student
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.sample.home.OnMessageClickListener
import com.example.sample.message.ui.ShowMessageActivity
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Professor
import kotlinx.coroutines.Dispatchers.Main


class SearchStudentActivity : AppCompatActivity(), OnItemClick, OnMessageClickListener {
    lateinit var binding: ActivitySearchStudentBinding

    var name: String? = null
    lateinit var adapter: ViewAllAdapter
    var professorLists = ArrayList<Student>()
    val studentViewModel: StudentViewModel by viewModels()
    val appViewModel: AppViewModel by viewModels()
    var user: User? = null
    var isHidden = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.hideFilters.setOnClickListener {
            if (!isHidden) {
                binding.filter.visibility = VISIBLE
//                binding.add.visibility = VISIBLE
//                binding.clear.visibility = VISIBLE
                isHidden = true
                binding.hideFilters.text = "Hide Filters"
            } else {
                binding.filter.visibility = GONE
//                binding.add.visibility = GONE
//                binding.clear.visibility = GONE

                isHidden = false
                binding.hideFilters.text = "Filters"
            }
        }


        binding.chipGrp.setOnCheckedChangeListener { chipgrp, integer ->
            Log.d(TAG, "onCreate: create 2 $integer")

            when (integer) {
                binding.rollNum.id -> {

                    name = Home.ROLL_NUM
                    binding.searchParam.queryHint = "Enter the $name"

                }
                binding.name.id -> {

                    name = Home.NAME
                    binding.searchParam.queryHint = "Enter the $name"


                }
                binding.departmentId.id -> {
                    name = Home.DEPART_ID
                    binding.searchParam.queryHint = "Enter the $name"

                }
                binding.classId.id -> {
                    name = Home.CLASS_ID
                    binding.searchParam.queryHint = "Enter the $name"

                }
                binding.professorId.id -> {
                    name = Home.PROF_ID
                    binding.searchParam.queryHint = "Enter the $name"

                }
                binding.hodId.id -> {

                    name = Home.HOD_ID
                    binding.searchParam.queryHint = "Enter the $name"

                }
                else -> {
                    binding.searchParam.queryHint = ""
                    name = ""


                }

            }
//            binding.searchParam.queryHint = "Enter the $name"
            binding.searchParam.requestFocus()
        }


        lifecycleScope.launch(Dispatchers.IO) {
            user = appViewModel.getUser()
            withContext(Main) {
                adapter = ViewAllAdapter(
                    professorLists, this@SearchStudentActivity,
                    name, user!!, this@SearchStudentActivity
                )
                binding.recyclerview.adapter = adapter
            }

            if (user?.loginID == Constants.PROFESSOR) {
                withContext(Dispatchers.Main) {

                    binding.apply {
                        classId.visibility = View.GONE
                        professorId.visibility = View.GONE
                        hodId.visibility = View.GONE
                        departmentId.visibility = View.GONE
                    }

                }
            }
        }
        studentViewModel.getStudentFromLiveData().observe(this) {
            Log.d(ContentValues.TAG, "onCreateView: ok ${it.size}")
            val alteredList = ArrayList<Student>()
            for (student in it) {
                if (student.clg_num.trim().isNotEmpty()) {
                    alteredList.add(student)
                }
            }
//            alteredList.addAll(it)
            val id = user?.loginID
            if (id == Constants.PROFESSOR) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val profId = user?.identifier
                    val professorStudents = ArrayList<Student>()
                    alteredList.forEach {
                        if (it.other?.professorID.toString() == profId) {
                            professorStudents.add(it)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        adapter.changeList(professorStudents)
                    }

                }
            } else if (id == Constants.ADMIN) {
                adapter.changeList(alteredList)

            }


        }
        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                name?.let {
                    adapter.changeSearchTerm(it)
                }
                adapter.filter.filter(newText)
                return false
            }

        }
//        binding.add.setOnClickListener {
//            when (name) {
//                Home.ROLL_NUM -> {
//                    binding.rollNum.isChecked = false
//                }
//                Home.NAME -> {
//                    binding.name.isChecked = false
//
//                }
//                Home.DEPART_ID -> {
//                    binding.departmentId.isChecked = false
//
//                }
//                Home.CLASS_ID -> {
//                    binding.classId.isChecked = false
//
//                }
//                Home.PROF_ID -> {
//                    binding.professorId.isChecked = false
//
//                }
//                Home.HOD_ID -> {
//                    binding.hodId.isChecked = false
//
//                }
//
//            }
//            binding.searchParam.setQuery("", false)
//            binding.searchParam.queryHint = ""
//        }


        binding.searchParam.setOnQueryTextListener(listener)
    }

    override fun onRemove(id: Int) {
        AlertDialog.Builder(this).setTitle("Warning")
            .setMessage("Are you sure you want to remove the Student?")
            .setPositiveButton("Yes") { interfacedata, integer ->
                removeStudent(id)
            }.setNegativeButton("No") { interfacedata, integer ->
            }.create().show()
    }


    private fun removeStudent(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            studentViewModel.removeStudent(id)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@SearchStudentActivity,
                    "Student  Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun edit(id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val intent = Intent(this@SearchStudentActivity, EditViewAllActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, Home.STUDENT_TITLE)
            intent.putExtra(Home.SHOW_ALL, id)
            startActivity(intent)
        }

    }

    override fun onclick(pos: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Home.CURR_ITEM, Home.STUDENT_TITLE)
        val obj = adapter.alteredLists[pos] as Parcelable
        intent.putExtra(Home.SHOW_ALL, obj)
        startActivity(intent)
    }

    override fun onClick(actor: Actor) {
        if (actor is Professor) {
            val name = "${actor.firstName} ${actor.lastName}"
            val intent = Intent(this, ShowMessageActivity::class.java)
            intent.putExtra(Home.ID, actor.professorID)
            intent.putExtra(Home.CURR_ITEM, Constants.PROFESSOR)
            intent.putExtra(Home.NAME, name)

            intent.putExtra(Home.USER, user)
            startActivity(intent)
        } else if (actor is Student) {
            val name = "${actor.firstName} ${actor.lastName}"
            val intent = Intent(this, ShowMessageActivity::class.java)
            intent.putExtra(Home.ID, actor.clg_num)
            intent.putExtra(Home.CURR_ITEM, Constants.STUDENT)
            intent.putExtra(Home.NAME, name)

            intent.putExtra(Home.USER, user)
            startActivity(intent)
        }

    }

}