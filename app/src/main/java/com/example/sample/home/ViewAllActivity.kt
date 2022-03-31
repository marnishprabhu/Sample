package com.example.sample.home

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.bottomfragments.announcement.OnItemClick
import com.example.sample.bottomfragments.home.model.People
import com.example.sample.databinding.ActivityViewAllBinding
import com.example.sample.home.adapter.ViewAllAdapter
import com.example.sample.home.edit.EditViewAllActivity
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.example.sample.R
import com.example.sample.message.ui.ShowMessageActivity
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.google.android.material.snackbar.Snackbar


class ViewAllActivity : AppCompatActivity(), OnItemClick, OnMessageClickListener {
    lateinit var binding: ActivityViewAllBinding
    private val listPeoples: ArrayList<People> = ArrayList()
    val appViewModel: AppViewModel by viewModels()
    val studentViewModel: StudentViewModel by viewModels()
    val professorViewModel: ProfessorViewModel by viewModels()
    lateinit var adapter: ViewAllAdapter
    var currentItem by Delegates.notNull<Int>()
    var isSearchVisible = false
    var loginID = 0
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        lifecycleScope.launch(IO) {
            user = appViewModel.getUser()!!
            loginID = user.loginID
            val recyclerView = binding.recyclerview
            withContext(Main) {
                adapter = ViewAllAdapter(
                    listPeoples,
                    this@ViewAllActivity,
                    user = user,
                    onmessageClickListener = this@ViewAllActivity)
                recyclerView.adapter = adapter
                val extras = intent.extras
                currentItem = extras?.get(Home.SHOW_ALL).toString().toInt()
                when (currentItem) {
                    Home.EVENT_TITLE -> {
                        fetchEvents()
                    }
                    Home.ADMIN_TITLE -> {
                        fetchAdmins()
                    }
                    Home.PROF_TITLE -> {
                        fetchProfessors()
                    }
                    Home.STUDENT_TITLE -> {
                        fetchStudents()
                    }
                }

            }
        }

        val listener = object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: $newText")

                adapter.filter.filter(newText)
                return true
            }
        }
        binding.searchView.setOnQueryTextListener(listener)

    }

    private fun fetchEvents() {
        appViewModel.getEvents().observe(this) {
            adapter.changeList(it)
            if (it == null || it.isEmpty()) {
                binding.hideText.visibility = VISIBLE
            }
            binding.progressLayout.visibility = GONE

        }
    }

    private fun fetchAdmins() {
        appViewModel.getAllAdminsLiveData().observe(this) {
            adapter.changeList(it)
            if (it == null || it.isEmpty()) {
                binding.hideText.visibility = VISIBLE

            }
            binding.progressLayout.visibility = GONE
        }
        binding.progressLayout.visibility = GONE

    }

    private fun fetchProfessors() {
        professorViewModel.getProfessorsLiveData().observe(this) {
            Log.d(TAG, "onQueryTextChange: ${it.size}")

            val query = binding.searchView.query
            if (query.isNotEmpty()) {
                return@observe
            }


            val alteredList = ArrayList<Professor>()
            for (professor in it) {
                if (professor.password?.trim()?.isNotEmpty() == true) {
                    alteredList.add(professor)
                }
            }
            adapter.changeList(alteredList)
            if (alteredList.isEmpty()) {
                binding.hideText.visibility = VISIBLE

            }
            binding.progressLayout.visibility = GONE

        }

    }

    private fun fetchStudents() {
        studentViewModel.getStudentFromLiveData().observe(this) {
            val alteredList = ArrayList<Student>()
            for (student in it) {
                if (student.clg_num.trim().isNotEmpty()) {
                    alteredList.add(student)
                }
            }
            adapter.changeList(alteredList)
            if (alteredList.isEmpty()) {
                binding.hideText.visibility = VISIBLE

            }
            binding.progressLayout.visibility = GONE

        }
    }


    override fun onRemove(id: Int) {
        val dialog = AlertDialog.Builder(this).setTitle("Warning")
        when (currentItem) {
            Home.EVENT_TITLE -> {
                dialog
                    .setMessage("Are you sure you want to remove the event?")
                    .setPositiveButton("Yes") { _, _ ->
                        removeEvent(id)
                    }.setNegativeButton("No") { _, _ ->
                    }.create().show()
            }
            Home.PROF_TITLE -> {
                dialog
                    .setMessage("Are you sure you want to remove the Professor?")
                    .setPositiveButton("Yes") { _, _ ->
                        removeProfessor(id)
                    }.setNegativeButton("No") { _, _ ->
                    }.create().show()
            }
            Home.STUDENT_TITLE -> {
                dialog
                    .setMessage("Are you sure you want to remove the Student?")
                    .setPositiveButton("Yes") { _, _ ->
                        removeStudent(id)
                    }.setNegativeButton("No") { _, _ ->
                    }.create().show()
            }
            Home.ADMIN_TITLE -> {
                dialog
                    .setMessage("Are you sure you want to remove the Admin?")
                    .setPositiveButton("Yes") { _, _ ->
                        removeAdmin(id)
                    }.setNegativeButton("No") { _, _ ->
                    }.create().show()
            }
        }

    }

    private fun removeAdmin(id: Int) {
        lifecycleScope.launch(IO) {
            appViewModel.removeAdmin(id)
            withContext(Main) {
                Toast.makeText(
                    this@ViewAllActivity,
                    "Admin Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun removeEvent(id: Int) {
        lifecycleScope.launch(IO) {
            appViewModel.deleteEvent(id)
            withContext(Main) {
                Toast.makeText(
                    this@ViewAllActivity,
                    "Event Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun removeStudent(id: Int) {
        lifecycleScope.launch(IO) {
            studentViewModel.removeStudent(id)
            withContext(Main) {
                Toast.makeText(
                    this@ViewAllActivity,
                    "Student  Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun removeProfessor(id: Int) {
        lifecycleScope.launch(IO) {
            professorViewModel.removeProfessor(id)
            withContext(Main) {
                Toast.makeText(
                    this@ViewAllActivity,
                    "Professor  Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun edit(id: String) {
        lifecycleScope.launch(IO) {
            val intent = Intent(this@ViewAllActivity, EditViewAllActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, currentItem)
            intent.putExtra(Home.SHOW_ALL, id)
            startActivity(intent)
        }

    }

    override fun onclick(pos: Int) {
        Log.d(TAG, "onclick: calling")
        if(loginID == Constants.STUDENT || loginID == Constants.PROFESSOR){
          return
        }
        val intent = Intent(this@ViewAllActivity, DetailActivity::class.java)
        intent.putExtra(Home.CURR_ITEM, currentItem)
        val obj = adapter.alteredLists[pos] as Parcelable
        intent.putExtra(Home.SHOW_ALL, obj)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                if (!isSearchVisible) {
                    binding.searchView.visibility = VISIBLE
                    isSearchVisible = true
                    item.setIcon(R.drawable.ic_close)

                } else {
                    binding.searchView.visibility = GONE
                    isSearchVisible = false
                    item.setIcon(R.drawable.ic_white_search)
                    binding.searchView.setQuery("", false)


                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onClick(actor: Actor) {
        if (actor is Professor) {
            val name = "${actor.firstName} ${actor.lastName}"
            val intent = Intent(this, ShowMessageActivity::class.java)
            intent.putExtra(Home.ID,actor.professorID)
            intent.putExtra(Home.NAME,name)
            intent.putExtra(Home.CURR_ITEM,Constants.PROFESSOR)
            intent.putExtra(Home.USER,user)
            startActivity(intent)
        } else if (actor is Student) {
            val name = "${actor.firstName} ${actor.lastName}"

            val intent = Intent(this, ShowMessageActivity::class.java)
            intent.putExtra(Home.ID,actor.clg_num)
            intent.putExtra(Home.CURR_ITEM,Constants.STUDENT)
            intent.putExtra(Home.USER,user)
            intent.putExtra(Home.NAME,name)

            startActivity(intent)
        }


    }
}