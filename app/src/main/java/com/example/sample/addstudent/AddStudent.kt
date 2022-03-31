package com.example.sample.addstudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import com.example.sample.databinding.ActivityAddStudentBinding
import android.app.DatePickerDialog
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sample.R
import java.util.*


class AddStudent : AppCompatActivity() {
    lateinit var binding:ActivityAddStudentBinding
    lateinit var myCalendar:Calendar
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//
        val navHostFragment = supportFragmentManager.findFragmentById(binding.addNavHost.id) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)



    }
    override fun onSupportNavigateUp(): Boolean {

        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to cancel?")
            .setPositiveButton("Yes") { a, b ->
                finish()
            }.setNegativeButton("No") { a, b ->
            }.create().show()
        return super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this,R.style.AlertDialogboxTheme)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to cancel?")
            .setPositiveButton("Yes") { a, b ->
                finish()
            }.setNegativeButton("No") { a, b ->
            }.create().show()
    }

}