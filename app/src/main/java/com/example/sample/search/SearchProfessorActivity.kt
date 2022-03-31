package com.example.sample.search

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.bottomfragments.announcement.OnItemClick
import com.example.sample.databinding.ActivitySearchSecondBinding
import com.example.sample.home.DetailActivity
import com.example.sample.home.OnMessageClickListener
import com.example.sample.home.adapter.ViewAllAdapter
import com.example.sample.home.edit.EditViewAllActivity
import com.example.sample.message.ui.ShowMessageActivity
import com.example.sample.room.entity.Actor
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.room.entity.User
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.*

class SearchProfessorActivity : AppCompatActivity(), OnItemClick, OnMessageClickListener {
    lateinit var binding: ActivitySearchSecondBinding
    var item = 1000
    var user: User? = null
    val appViewModel: AppViewModel by viewModels()

    lateinit var adapter: ViewAllAdapter
    var professorLists = ArrayList<Professor>()
    val professorViewModel: ProfessorViewModel by viewModels()

    var name: String? = null
    var isHidden = false
    var isLoading = false
    var isScrolledDown = false
    var isScrolledTop = false
    var isChanged = false
    var lastValue = -10
    var lastRes = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.hideFilters.setOnClickListener {
                if (!isHidden) {
                    binding.filter.visibility = VISIBLE
                    isHidden = true
                    binding.hideFilters.text = "Hide Filters"
//                    binding.add.visibility = VISIBLE
//                    binding.clear.visibility = VISIBLE
//                    binding.apply.visibility = VISIBLE

                } else {
                    binding.filter.visibility = View.GONE
                    isHidden = false
                    binding.hideFilters.text = "Filters"
//                    binding.add.visibility = GONE
//                    binding.clear.visibility = GONE
//                    binding.apply.visibility = GONE


                }
            }

            binding.chipGrp.setOnCheckedChangeListener { chipgrp, integer ->
                when (integer) {

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
                    else -> {
                        binding.searchParam.queryHint = ""
                        name = ""


                    }

                }
                binding.searchParam.requestFocus()

            }

            adapter = ViewAllAdapter(
                professorLists,
                this,
                name, onmessageClickListener = this@SearchProfessorActivity
            )

            binding.recyclerview.adapter = adapter
            binding.recyclerview.setHasFixedSize(true)
            lifecycleScope.launch(Dispatchers.Default) {
                user = appViewModel.getUser()

                val professorsList = professorViewModel.getAllProfessors()
                val alteredList = ArrayList<Professor>()
                for (professor in professorsList) {
                    if (professor.password?.trim()?.isNotEmpty() == true) {
                        alteredList.add(professor)
                    }
                }
                withContext(Dispatchers.Main) {
                    adapter.changeList(alteredList)
                    binding.progress.visibility = GONE
                }
            }
            binding.searchParam.clearFocus()
//            binding.add.setOnClickListener {
//                when (name) {
//                    Home.ROLL_NUM -> {
//                        binding.handlingId.isChecked = false
//                    }
//                    Home.NAME -> {
//                        binding.name.isChecked = false
//
//                    }
//                    Home.DEPART_ID -> {
//                        binding.departmentId.isChecked = false
//
//                    }
//                    Home.CLASS_ID -> {
//                        binding.classId.isChecked = false
//
//                    }
//                    Home.PROF_ID -> {
//                        binding.professorId.isChecked = false
//
//                    }
//                    Home.HOD_ID -> {
//                        binding.hodId.isChecked = false
//
//                    }
//
//                }
//                adapter.changeAnotherFilter(true)
//                binding.searchParam.setQuery("", false)
//
//            }
//            binding.apply.setOnClickListener {
//                binding.hideFilters.text = "Filters"
//                binding.add.visibility = GONE
//                binding.clear.visibility = GONE
//                binding.apply.visibility = GONE
//                binding.filter.visibility = GONE
//                isHidden = false
//
//            }
//            binding.clear.setOnClickListener {
//                Log.d(TAG, "onCreate: clear")
//                adapter.changeAnotherFilter(false)
//                binding.searchParam.setQuery("", false)
//
//            }
            val listener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    name?.let {
                        adapter.changeSearchTerm(it)
                    }
                    adapter.filter.filter(newText)
                    return true
                }

            }
            binding.searchParam.setOnQueryTextListener(listener)

        }


//    private fun hideItems() {
//        binding.toolbar.visibility = GONE
//        binding.hideFilters.visibility = GONE
//        binding.apply {
//            addButton.visibility = GONE
//            add.visibility = GONE
//        }
//    }
//
//    private fun showItems() {
//        binding.toolbar.visibility = VISIBLE
//        binding.hideFilters.visibility = VISIBLE
//        binding.apply {
//            addButton.visibility = VISIBLE
//            add.visibility = VISIBLE
//        }
//    }

//    private fun performHidingOperations() {
//        val firstPosition = (binding.recyclerview.layoutManager as LinearLayoutManager)
//            .findFirstVisibleItemPosition()
//        Log.d(TAG, "performHidingOperations: $firstPosition")
//






//        if (lastValue == 1) {
//            val firstPosition = (binding.recyclerview.layoutManager as LinearLayoutManager)
//                .findFirstCompletelyVisibleItemPosition()
//            if (firstPosition < lastRes || (firstPosition == lastRes && firstPosition == 0)) {
//                showItems()
//            } else if (firstPosition > lastRes) {
//                hideItems()
//            }
//            Log.d(TAG, "performHidingOperations:  $firstPosition $lastRes")
//            lastRes = firstPosition
//        }


//    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ok")
    }

    override fun onRemove(id: Int) {
        AlertDialog.Builder(this).setTitle("Warning")
            .setMessage("Are you sure you want to remove the Professor?")
            .setPositiveButton("Yes") { interfacedata, integer ->
                removeProfessor(id)
            }.setNegativeButton("No") { interfacedata, integer ->
            }.create().show()


    }


    private fun removeProfessor(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            professorViewModel.removeProfessor(id)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@SearchProfessorActivity,
                    "Professor  Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun edit(id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val intent = Intent(this@SearchProfessorActivity, EditViewAllActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, Home.PROF_TITLE)
            intent.putExtra(Home.SHOW_ALL, id)
            startActivity(intent)
        }

    }

    override fun onclick(pos: Int) {
        val intent = Intent(this@SearchProfessorActivity, DetailActivity::class.java)
        intent.putExtra(Home.CURR_ITEM, Home.PROF_TITLE)
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
            intent.putExtra(Home.USER, user)
            intent.putExtra(Home.NAME, name)

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

