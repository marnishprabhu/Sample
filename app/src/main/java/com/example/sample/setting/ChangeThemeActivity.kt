package com.example.sample.setting

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.sample.databinding.ActivityChangeThemeBinding
import com.example.sample.room.entity.User
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangeThemeActivity : AppCompatActivity() {
    val appViewModel: AppViewModel by viewModels()
    lateinit var binding: ActivityChangeThemeBinding
    var user: User? = null
    var isDark = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        Log.d(TAG, "onCreate: night mode $nightMode")
        val isDarkthemeOn = isDarkThemeOn()
        when (nightMode) {
            AppCompatDelegate.MODE_NIGHT_YES  -> {
                binding.apply {
                    dark.isChecked = true
                    light.isChecked = false
                }
            }
            AppCompatDelegate.MODE_NIGHT_UNSPECIFIED->{
                if(isDarkthemeOn){
                    binding.apply {
                        dark.isChecked = true
                        light.isChecked = false
                    }
                }
                else{
                    binding.apply {
                        light.isChecked = true
                        dark.isChecked = false
                    }
                }
            }
            AppCompatDelegate.MODE_NIGHT_NO  -> {
                binding.apply {
                    light.isChecked = true
                    dark.isChecked = false

                }

            }

        }


        binding.dark.setOnClickListener {
            binding.light.isChecked = false
            lifecycleScope.launch(IO) {
                if (user == null) {
                    user = appViewModel.getUser()!!
                }
                isDark = true
                user!!.isDarkMode = isDark
                appViewModel.changeUser(user!!)
                withContext(Main) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }

        binding.light.setOnClickListener {
            binding.dark.isChecked = false
            lifecycleScope.launch(IO) {
                if (user == null) {
                    user = appViewModel.getUser()!!
                }
                isDark = false
                user!!.isDarkMode = isDark
                appViewModel.changeUser(user!!)
                withContext(Main) {
                    Log.d(TAG, "onCreate: changing to light ")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

    }
    private fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}