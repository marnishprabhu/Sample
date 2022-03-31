package com.example.sample.addacademicdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sample.addacademicdata.adapter.AcademicAdapter
import com.example.sample.addacademicdata.adapter.OnStudentAcademicClicked
import com.example.sample.addacademicdata.add.AcademicEditActivity
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityAddAcademicBinding
import com.example.sample.room.entity.User
import com.example.sample.room.entity.Student
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAcademicActivity : AppCompatActivity(), OnStudentAcademicClicked {
    lateinit var binding: ActivityAddAcademicBinding
    var data = ArrayList<Student>()
    lateinit var adapter: AcademicAdapter
    val appviewModel: AppViewModel by viewModels()
    val studentViewModel: StudentViewModel by viewModels()
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAcademicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = AcademicAdapter(data, this)
        binding.recyclerview.adapter = adapter
        getStudents()
    }

    private fun getStudents() {
        lifecycleScope.launch(IO) {
            user = if (appviewModel.user != null) {
                appviewModel.user
            } else {
                appviewModel.getUser()
            }
            val profId = user?.identifier
            withContext(Main) {
                studentViewModel.getAllStudentsForProfessorID(profId.toString())
                    .observe(this@AddAcademicActivity)
                    {
                        data = it as ArrayList<Student>
                        adapter.changeList(it)
                    }
            }


        }
    }

    override fun onClick(position: Int) {
        val intent = Intent(this@AddAcademicActivity, AcademicEditActivity::class.java)
        intent.putExtra(Home.CURR_ITEM,data[position])
        intent.putExtra(Home.ID,data[position].id)
        startActivity(intent)
    }
}