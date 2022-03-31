package com.example.sample.addprofessor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sample.R
import com.example.sample.databinding.ActivityAddProfessorBinding
import java.util.*

class AddProfessor : AppCompatActivity() {

    lateinit var binding: ActivityAddProfessorBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProfessorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.addProfessorNavHost.id) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(

            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        showDialog()

        return super.onSupportNavigateUp()

    }

    override fun onBackPressed() {
        val currentId = navController.currentDestination?.id
        if(currentId == R.id.selectDataFragment2){
           super.onBackPressed()
        }
        else{
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this,R.style.AlertDialogboxTheme)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to cancel?")
            .setPositiveButton("Yes") { a, b ->
                finish()
            }.setNegativeButton("No") { a, b ->
            }.create().show()
    }
}