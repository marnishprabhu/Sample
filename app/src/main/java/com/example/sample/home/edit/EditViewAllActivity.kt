package com.example.sample.home.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sample.R
import com.example.sample.databinding.ActivityEditViewAllBinding
import com.example.sample.home.edit.professor.EditProfessorFragment
import com.example.sample.home.edit.student.EditStudentFragment
import com.example.sample.utils.Home
import kotlin.properties.Delegates

class EditViewAllActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditViewAllBinding
    var currentItem by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        currentItem = data?.get(Home.CURR_ITEM).toString().toInt()
        val identi = data?.get(Home.SHOW_ALL).toString()

        when (currentItem) {
            Home.EVENT_TITLE -> {
                val bundle = Bundle()
                bundle.putString(Home.SHOW_ALL, identi)
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        binding.editViewAllHost.id,
                        EditEventFragment::class.java,
                        bundle,
                        "Tag"
                    )
                    .commit()

            }
            Home.PROF_TITLE -> {
                val bundle = Bundle()
                bundle.putString(Home.SHOW_ALL, identi)
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        binding.editViewAllHost.id,
                        EditProfessorFragment::class.java,
                        bundle,
                        "Tag"
                    )
                    .commit()
            }
            Home.STUDENT_TITLE -> {
                val bundle = Bundle()
                bundle.putString(Home.SHOW_ALL, identi)
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        binding.editViewAllHost.id,
                        EditStudentFragment::class.java,
                        bundle,
                        "Tag"
                    )
                    .commit()
            }
            Home.ADMIN_TITLE -> {

            }
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_close)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}