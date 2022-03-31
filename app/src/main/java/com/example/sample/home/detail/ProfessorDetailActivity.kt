package com.example.sample.home.detail

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.databinding.ActivityProfessorDetailBinding
import com.example.sample.home.detail.professor.MainProfessorDetailFragment
import com.example.sample.home.edit.EditViewAllActivity
import com.example.sample.room.entity.Professor
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfessorDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfessorDetailBinding
    val professorViewModel: ProfessorViewModel by viewModels()
    val appViewModel: AppViewModel by viewModels()
    lateinit var professor: Professor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfessorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        professor = intent.extras?.get(Home.SHOW_ALL) as Professor
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, MainProfessorDetailFragment(professor))
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = appViewModel.getUser()!!

            withContext(Dispatchers.Main) {
                if (user.loginID == Constants.ADMIN) {
                    menuInflater.inflate(R.menu.announcment_item_menu, menu)

                    menu.findItem(R.id.remove).setOnMenuItemClickListener {

                        removeProfessor(professor.professorID.toInt())
                        return@setOnMenuItemClickListener true
                    }
                    menu.findItem(R.id.edit).setOnMenuItemClickListener {
                        edit(professor.professorID.toString())

                        return@setOnMenuItemClickListener true

                    }
                }
            }
        }


        return true
    }

    private fun removeProfessor(id: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            professorViewModel.removeProfessor(id)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@ProfessorDetailActivity,
                    "Professor  Successfully deleted.",
                    Toast.LENGTH_SHORT
                ).show()
                super.onBackPressed()
            }
        }
    }

    private fun edit(id: String) {
        Log.d(ContentValues.TAG, "edit: $id")
        lifecycleScope.launch(Dispatchers.IO) {
            val intent = Intent(this@ProfessorDetailActivity, EditViewAllActivity::class.java)
            intent.putExtra(Home.CURR_ITEM, Home.PROF_TITLE)
            intent.putExtra(Home.SHOW_ALL, id)
            startActivity(intent)
        }
    }
}