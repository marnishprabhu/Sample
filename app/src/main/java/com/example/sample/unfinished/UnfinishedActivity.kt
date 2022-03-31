package com.example.sample.unfinished

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.sample.R
import com.example.sample.addprofessor.viewmodel.ProfessorViewModel
import com.example.sample.addstudent.viewmodel.StudentViewModel
import com.example.sample.databinding.ActivityUnfinishedBinding
import com.example.sample.room.entity.Professor
import com.example.sample.room.entity.Student
import com.example.sample.unfinished.adapter.UnfinishedAdapter
import com.example.sample.unfinished.adapter.UnfinishedCompleteNow
import com.example.sample.utils.Constants
import com.example.sample.utils.Home
import com.example.sample.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnfinishedActivity : AppCompatActivity(), UnfinishedCompleteNow {
    lateinit var binding: ActivityUnfinishedBinding
    lateinit var studentadapter: UnfinishedAdapter
    lateinit var profadapter: UnfinishedAdapter

    private val appViewModel: AppViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    private val professorViewModel: ProfessorViewModel by viewModels()
    private var data = ArrayList<Any>()
    private  var studentUnfinishedLists = ArrayList<Any>()
    private   var professorsUnfinishedLists = ArrayList<Any>()
    private  var isStudentClicked = false
    private var isProfessorClicked = false
    private val rotateUp = lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.view_profile_up
        )
    }
    private  var position = 0
    private var type = 0

    private val rotateDown = lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.view_profile_down
        )
    }
    private  var currentLogin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnfinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.studentCard.setOnClickListener {
            if (!isStudentClicked) {
                binding.studentLay.visibility = VISIBLE
                isStudentClicked = true
                binding.studShow.startAnimation(rotateDown.value)

            } else {
                binding.studentLay.visibility = GONE
                isStudentClicked = false
                binding.studShow.startAnimation(rotateUp.value)

            }
        }
        binding.professorCard.setOnClickListener {
            if (!isProfessorClicked) {
                binding.professorLay.visibility = VISIBLE
                isProfessorClicked = true
                binding.profShow.startAnimation(rotateDown.value)
            } else {
                binding.professorLay.visibility = GONE
                isProfessorClicked = false
                binding.profShow.startAnimation(rotateUp.value)
            }
        }
        studentadapter = UnfinishedAdapter(studentUnfinishedLists, this)
        profadapter = UnfinishedAdapter(professorsUnfinishedLists, this)
        binding.recyclerviewProf.adapter = profadapter
        binding.recyclerviewStud.adapter = studentadapter
    }

    private fun fetchData() {
        lifecycleScope.launch(IO) {
            currentLogin = appViewModel.getUser()?.loginID!!
            when (currentLogin) {
                Constants.ADMIN -> {
                    studentUnfinishedLists =
                        studentViewModel.getStudentUnfinishedWork() as ArrayList<Any>
                    professorsUnfinishedLists =
                        professorViewModel.getProfessorUnfinishedWork() as ArrayList<Any>
                    if (studentUnfinishedLists.isEmpty() && professorsUnfinishedLists.isEmpty()) {
                        withContext(Main) {
                            binding.hide.visibility = VISIBLE
                        }
                    }
                    withContext(Main) {
                        studentadapter.changeList(studentUnfinishedLists)
                        profadapter.changeList(professorsUnfinishedLists)
                        binding.profNum.text = professorsUnfinishedLists.size.toString()
                        binding.studNum.text = studentUnfinishedLists.size.toString()
                        if (studentUnfinishedLists.isEmpty()) {
                            binding.studentCard.visibility = GONE
                        }
                        if (professorsUnfinishedLists.isEmpty()) {
                            binding.professorCard.visibility = GONE
                        }

                    }
                }
                Constants.PROFESSOR -> {
                    withContext(Main) {
                        binding.professorCard.visibility = GONE
                    }
                    studentUnfinishedLists =
                        studentViewModel.getStudentUnfinishedWork() as ArrayList<Any>
                    withContext(Main) {
                        studentadapter.changeList(studentUnfinishedLists)
                        binding.studNum.text = studentUnfinishedLists.size.toString()


                    }
                }
            }

        }
    }

    override fun onCompleteNow(position: Int, type: Int) {
        this.position = position
        this.type = type
        when (type) {
            Constants.PROFESSOR -> {
                val professor = professorsUnfinishedLists[position] as Professor

                moveToDesiredActivity(professor)
                binding.professorLay.visibility = GONE
                isProfessorClicked = false
                binding.profShow.startAnimation(rotateUp.value)

            }
            Constants.STUDENT -> {
                val student = studentUnfinishedLists[position] as Student
                moveToDesiredActivity(student)
                binding.studentLay.visibility = GONE
                isStudentClicked = false
                binding.studShow.startAnimation(rotateUp.value)
            }

        }

    }

    override fun onRemove(position: Int, type: Int) {
        this.position = position
        this.type = type
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to remove ?")
            .setPositiveButton("Yes") { a, b ->
                removeItem(position, type)
            }.setNegativeButton("No") { a, b ->
            }.create().show()


    }

    private fun removeItem(position: Int, type: Int) {
        lifecycleScope.launch(IO) {
            when (type) {
                Constants.PROFESSOR -> {
                    val professor = professorsUnfinishedLists[position] as Professor
                    val removedItem =
                        professorViewModel.removeProfessor(professor.professorID.toInt())
                    if (removedItem > 0) {
                        Snackbar.make(
                            binding.root,
                            "Item removed Successfully",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        fetchData()
                    } else {
                        Snackbar.make(binding.root, "Cannot remove item", Snackbar.LENGTH_SHORT)
                            .show()
                    }


                }
                Constants.STUDENT -> {

                    val student = studentUnfinishedLists[position] as Student
                    Log.d(TAG, "removeItem: ${student.univ_num}")
                    val removedItem = studentViewModel.removeStudentByUniv(student.univ_num)
                    if (removedItem > 0) {
                        Snackbar.make(
                            binding.root,
                            "Item removed Successfully",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        fetchData()

                    } else {
                        Snackbar.make(binding.root, "Cannot remove item", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
    }

    private fun moveToDesiredActivity(student: Student) {
        val intent = Intent(this, EditUnfinishedActivity::class.java)
        Log.d(TAG, "moveToDesiredActivity: $student")
        intent.putExtra(Home.CURR_ITEM, student)
        intent.putExtra(Home.ID, student.id)
        startActivity(intent)

    }

    private fun moveToDesiredActivity(professor: Professor) {
        val intent = Intent(this, EditUnfinishedActivity::class.java)
        intent.putExtra(Home.CURR_ITEM, professor)
        startActivity(intent)


    }

    override fun onStart() {
        super.onStart()
        fetchData()
    }
}